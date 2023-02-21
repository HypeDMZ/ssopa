import React, { useState, useEffect , useRef} from 'react';
import {useParams} from 'react-router-dom';
import {getCookie} from "../../function/cookie";
import SockJS from 'sockjs-client';
import {Stomp} from '@stomp/stompjs';
import axios from 'axios';

function InRoom(){
    const {roomId} = useParams();

    const [room, setRoom] = useState({});
    const [message, setMessage] = useState('');
    const [messages, setMessages] = useState([]);

    const headers = {Authorization: getCookie('token').accessToken};

    const sock = useRef(null);
    const ws = useRef(null);
    const reconnect = useRef(0);

    const findRoom = async () => {
        const response = await axios.get(`https://ssopa02.com/api/chat/room/${roomId}`);
        setRoom(response.data);
    };

    const sendMessage = () => {
        ws.current.send('/app/chat/message', headers, JSON.stringify({ type: 'TALK', roomId, sender:"1" , message }));
        setMessage('');
    };

    const recvMessage = (recv) => {
        setMessages(prevMessages => [...prevMessages, { type: recv.type, sender: recv.type === 'ENTER' ? '[알림]' : recv.sender, message: recv.message }]);
    };

    useEffect(() => {
        sock.current = new SockJS('ssopa02.com/api/ws/chat');
        ws.current = Stomp.over(sock.current);
        findRoom();

        ws.current.connect(headers, (frame) => {
            ws.current.subscribe(`/topic/chat/room/${roomId}`, (message) => {
                const recv = JSON.parse(message.body);
                recvMessage(recv);
            }, headers);
            ws.current.send('/app/chat/message', headers, JSON.stringify({ type: 'ENTER', roomId, sender:"1" }));
        }, (error) => {
            if (reconnect.current++ <= 5) {
                setTimeout(() => {
                    console.log('connection reconnect');
                    sock.current = new SockJS('/ws/chat');
                    ws.current = Stomp.over(sock.current);
                    ws.current.connect(headers);
                }, 10 * 1000);
            }
        });

        return () => {
            ws.current.disconnect();
            sock.current.close();
        };
    }, [roomId]);

    return (
        <div className="container">
            <div>
                <h2>{room.name}</h2>
            </div>
            <div className="input-group">
                <div className="input-group-prepend">
                    <label className="input-group-text">내용</label>
                </div>
                <input type="text" className="form-control" value={message} onChange={(e) => setMessage(e.target.value)} onKeyPress={(e) => e.key === 'Enter' && sendMessage()} />
                <div className="input-group-append">
                    <button className="btn btn-primary" type="button" onClick={sendMessage}>보내기</button>
                </div>
            </div>
            <ul className="list-group">
                {messages.map((message, index) => (
                    <li className="list-group-item" key={index}>
                        {message.sender} - {message.message}
                    </li>
                ))}
            </ul>
        </div>
    )
}

export {InRoom};