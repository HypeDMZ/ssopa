import React, { useState, useEffect } from 'react';
import axios, {get} from 'axios';
import {setCookie,getCookie,removeCookie} from "../../function/cookie";
import {Link, useNavigate} from "react-router-dom";
import styled from "../../css/ChattingRoom.module.css";
import {InRoom} from "./InRoom";

function RoomList(){
    const Navigate = useNavigate();

    const [InputRoomName,roomNameChange] = useState('');
    const [chatRooms, chatRoomsChange] = useState([]);
    const [chatRoom, setChatRoom] = useState(false);
    const [sendId, setSendId] = useState('');

    useEffect(()=>{
        findAllRooms();
    },[])

    const findAllRooms = () => {
        axios.get("/api/chat/rooms").then((response)=>{
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

            axios.post("/api/chat/room",
                {
                    // data to sent to the server - post body
                    // it can be an empty object
                },
                {
                    // specify query parameters
                    params: {
                        name: InputRoomName,

                    },
                })
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
        // <div>
        //     <div style={{display:"flex"}}>
        //         <input type='text' value={InputRoomName} onChange={(e)=>{roomNameChange(e.target.value)}}/>
        //         <button onClick={()=>{createRoom()}}>방 생성</button>
        //     </div>
        //     <ul style={{backgroundColor:"blue"}}>
        //         {chatRooms.map(roomData => (
        //             <li key={roomData.roomId}>
        //                 <button>
        //                     <Link to={`/chat/room/enter/${roomData.roomId}`}>
        //                         {roomData.roomName}
        //                     </Link>
        //                 </button>
        //             </li>
        //         ))}
        //     </ul>
        // </div>
        <div className={styled.post_container}>
            <div className={styled.post_nav}></div>

            <div className={styled.post_main_container}>

                <div className={styled.post_main_left}>
                    <div className={styled.post_main_contents}>
                        <div className={styled.post_friend_nav}> 채팅방 목록 </div>
                        {chatRooms.map(roomData => (
                            <div className={styled.post_friend_list} key={roomData.roomId} onClick={()=>{
                                setChatRoom(true);
                                setSendId(roomData.roomId);
                            }}>
                                {roomData.roomName}
                            </div>
                        ))}
                    </div>
                </div>


                <div className={styled.post_main_right}>
                    <div className={styled.post_main_contents}>
                        <div className={styled.post_right_nav}>
                            <div className={styled.post_search}>
                                <div style={{width : "15%", textAlign: "center"}}>  <p style={{fontSize : "15px", color : "white"}}>채팅방 생성</p> </div>
                                <input style={{width : "70%", height : "80%", marginLeft : "2%",borderRadius : "15px"}} placeholder= "🔍 채팅방 이름..."
                                       value={InputRoomName} onChange={(e)=>{roomNameChange(e.target.value)}}/>
                                <button onClick={()=>{createRoom()}} style={{borderRadius : "25px", width : "10%", marginLeft : "2%" , backgroundColor : "white", color : "#F2B284", border : "none"}}> +생성 </button>
                            </div>
                        </div>
                        <div style={{height: "90%"}}>
                            { chatRoom ? <InRoom id={sendId}/> : null}
                        </div>
                    </div>
                </div>

            </div>

        </div>
    )
}

export {RoomList};