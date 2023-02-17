import React, { useState, useEffect } from 'react';
import { Link, Route, Routes , BrowserRouter} from 'react-router-dom';
import axios from "axios";
import './App.css';
import { Main } from "./component/Main";
import { Login } from "./component/Login";
import { Signup } from "./component/Signup";
import { Post } from "./component/Post";



function App() {

    return (
        <BrowserRouter>
            <div>
                <Routes>
                    <Route path="/" element={<Main />}> </Route>
                    <Route path="/auth/login" element={<Login />}></Route>
                    <Route path="/auth/signup" element={<Signup />}></Route>
                    <Route path="/Post" element={<Post/>}></Route>
                </Routes>
            </div>
        </BrowserRouter>
    );
}

export default App;
