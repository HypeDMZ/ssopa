import React, { useState, useEffect , useRef} from 'react';
import {useParams} from 'react-router-dom';
import {getCookie} from "../../function/cookie";
import SockJS from 'sockjs-client';
import {Stomp} from '@stomp/stompjs';
import axios from 'axios';
import styled from "../../css/ChattingRoom.module.css";

function InRoom(props){
    // const {roomId} = useParams();
    const roomId = props.id;

    const [room, setRoom] = useState({});
    const [message, setMessage] = useState('');
    const [messages, setMessages] = useState([]);

    const headers = {Authorization: "Bearer "+getCookie('token').accessToken};

    const messagesEndRef = useRef(null);
    const sock = useRef(null);
    const ws = useRef(null);
    const reconnect = useRef(0);

    const findRoom = async () => {
        const response = await axios.get(`/api/chat/room/${roomId}`);
        setRoom(response.data);
    };

    const sendMessage = () => {
        ws.current.send('/app/chat/message', headers, JSON.stringify({ type: 'TALK', roomId, sender:"1" , message }));
        setMessage('');
    };

    const recvMessage = (recv) => {
        setMessages(prevMessages => [...prevMessages, { type: recv.type, sender: recv.type === 'ENTER' ? '[알림]' : recv.sender, message: recv.message }]);
    };

    const scrollToBottom = () => {
        // ref로 찾은 div element의 scrollHeight와 clientHeight를 이용하여
        // div element의 스크롤 위치를 계산합니다.
        messagesEndRef.current.scrollIntoView({ behavior: 'smooth' });
    };

    useEffect(() => {
        scrollToBottom();
    }, [messages]);

    useEffect(() => {
        sock.current = new SockJS('/api/ws/chat');
        ws.current = Stomp.over(sock.current);
        setMessages([]);
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
                    sock.current = new SockJS('/api/ws/chat');
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
        <div style={{width: "100%" , height: "100%", color:"black"}}>
            <div>
                <h2>{room.name}</h2>
            </div>
            <div style={{height: "90%",overflowY: "scroll"}}>
                <ul>
                    {messages.map((message, index) => (
                        <li key={index}>
                            {message.sender} - {message.message}
                        </li>
                    ))}
                </ul>
                <div ref={messagesEndRef} />
            </div>
            <div style={{height: "10%", float:"left"}}>
                <input type="text" value={message} onChange={(e) => setMessage(e.target.value)} onKeyPress={(e) => e.key === 'Enter' && sendMessage()} />
                <button type="button" onClick={sendMessage}>보내기</button>
            </div>
        </div>
    )
}


export {InRoom};