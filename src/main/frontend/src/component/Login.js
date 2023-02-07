import React, { useState, useEffect } from 'react';
import {Link} from "react-router-dom";
import { post, put, axios } from 'axios';
function Login(props){
    let [아이디, 아이디변경] = useState('');
    let [비밀번호, 비밀번호변경] = useState('');
    return (
        <div>
            <h4>홈</h4>
            <input type={"text"} onChange={ (e)=>{
                아이디변경(e.target.value);
            }}></input><br/>
            <input type={"password"} onChange={ (e)=>{
                비밀번호변경(e.target.value);
            }}></input><br/>
            <button onClick={ ()=>{

            }}>로그인</button>
        </div>
    )
}

export { Login };