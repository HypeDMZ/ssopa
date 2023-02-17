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


function App() {

    return (
        <BrowserRouter>
            <div>
                <Routes>
                    <Route path="/" element={<Main />}></Route>
                    <Route path="/auth/login" element={<Login />}></Route>
                    <Route path="/auth/signup" element={<Signup />}></Route>
                    <Route path="/Post" element={<Post/>}></Route>

                    <Route path="*" element={<div>여기 아이다</div>}></Route>
                    <Route path="/auth/findid" element={<FindId/>}></Route>
                    <Route path="/auth/findpw" element={<FindPw/>}></Route>
                    <Route path="/auth/showid" element={<ShowId/>}></Route>
                    <Route path="/auth/showpw" element={<ShowPw/>}></Route>
                </Routes>
            </div>
        </BrowserRouter>
    );
}

export default App;
