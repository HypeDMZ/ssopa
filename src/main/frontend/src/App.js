import React, { useState, useEffect } from 'react';
import { Link, Route, Routes , BrowserRouter} from 'react-router-dom';
import axios from "axios";
import './App.css';
import { Main } from "./component/Main";
import { Login } from "./component/Login";
import { Signup } from "./component/Signup";
import { Post } from "./component/Post";
import { FindId } from "./component/FindId";
import { FindPw } from "./component/FindPw";
import { ShowId } from "./component/ShowId";
import { ShowPw } from "./component/ShowPw";
import { InRoom } from "./component/room/InRoom";
import { RoomList } from "./component/room/RoomList";
import { Write } from "./component/Write";
import { ChoosePost } from "./component/ChoosePost";
import { ChattingRoom } from "./component/ChattingRoom";
import {AfterSearchingPost} from "./component/AfterSearchingPost";
import {AfterChooseMyPost} from "./component/AfterChooseMyPost";
import {AfterClickBookMark} from "./component/AfterClickBookMark";
import {MyCommentPost} from "./component/MyCommentPost";
function App() {

    return (
        <BrowserRouter>
            <div>
                <Routes>
                    <Route path="/" element={<Main />}></Route>
                    <Route path="/auth/login" element={<Login />}></Route>
                    <Route path="/auth/signup" element={<Signup />}></Route>
                    <Route path="/auth/findid" element={<FindId/>}></Route>
                    <Route path="/auth/findpw" element={<FindPw/>}></Route>
                    <Route path="/auth/showid/:email" element={<ShowId/>}></Route>
                    <Route path="/auth/showpw/:email" element={<ShowPw/>}></Route>
                    <Route path="/auth/write/:email" element={<Write/>}></Route>

                    <Route path="/post" element={<Post/>}></Route>
                    <Route path="/post/add" element={<Write/>}/>

                    {/*chat*/}
                    <Route path="/chat/list" element={<RoomList/>}></Route>
                    <Route path="/chat/room/enter/:roomId" element={<InRoom/>}></Route>
                    <Route path="/auth/choosePost" element={<ChoosePost/>}></Route>
                    <Route path="/auth/chattingRoom" element={<ChattingRoom/>}></Route>
                    <Route path="/auth/AfterSearchingPost" element={<AfterSearchingPost/>}></Route>
                    <Route path="/auth/AfterChooseMyPost" element={<AfterChooseMyPost/>}></Route>
                    <Route path="/auth/AfterClickBookMark" element={<AfterClickBookMark/>}></Route>
                    <Route path="/auth/MyCommentPost" element={<MyCommentPost/>}></Route>

                    <Route path="*" element={<div>여기 아이다</div>}></Route>
                </Routes>
            </div>
        </BrowserRouter>
    );
}

export default App;
