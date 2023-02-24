import React, { useState, useEffect } from 'react';
import axios, {get} from 'axios';
import {setCookie,getCookie,removeCookie} from "../../function/cookie";
import {Link, useNavigate} from "react-router-dom";
function RoomList(){
    const Navigate = useNavigate();

    const [InputRoomName,roomNameChange] = useState('');
    const [chatRooms, chatRoomsChange] = useState([]);

    useEffect(()=>{
        findAllRooms();
    },[])

    const findAllRooms = () => {
        axios.get("http://ssopa02.com/api/chat/rooms").then((response)=>{
            console.log(response.data)
            chatRoomsChange(response.data);
        })
    }

    const createRoom = () => {
        if('' === InputRoomName){
            alert("방 제목을 입력해 주십시요.");
            return;
        }
        else{
            axios.defaults.headers.common['Authorization'] = getCookie('token').accessToken;

            console.log(axios.defaults.headers.common['Authorization']);

            axios.post("https://ssopa02.com/api/chat/room",
                {params: {name : InputRoomName}})
                .then((response)=>{
                    alert(response.data.roomName + "방 개설에 성공하였습니다.");
                    roomNameChange('');
                    findAllRooms();
                })
                .catch((response)=>{
                    alert("채팅방 개설에 실패하였습니다.");
                })
        }
    }



    return(
        <div>
            <div style={{display:"flex"}}>
                <input type='text' value={InputRoomName} onChange={(e)=>{roomNameChange(e.target.value)}}/>
                <button onClick={()=>{createRoom()}}>방 생성</button>
            </div>
            <ul style={{backgroundColor:"blue"}}>
                {chatRooms.map(roomData => (
                    <li key={roomData.roomId}>
                        <button>
                            <Link to={`/chat/room/enter/${roomData.roomId}`}>
                                {roomData.roomName}
                            </Link>
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    )
}

export {RoomList};