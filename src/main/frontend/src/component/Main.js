import React , { useState, useEffect } from "react";
import { Link, Route, Routes , BrowserRouter} from 'react-router-dom';
import "../css/Main.css";
import logo from"../img/logo.png";
import axios from "axios";
import {setCookie,getCookie,removeCookie} from "../function/cookie";
function Main(props){
    return (
        <div className={"topbar"}>
            <img src={logo} style={{ width:200, height:200}}/><br/>
            <button className={"button"} style={{
                top: '312px',
                background: 'red',
                color: 'white'
            }}><Link to="/auth/login" style={{color: 'white', textDecoration: "none"}}>로그인</Link></button>
            <button className={"button"} style={{
                top: '412px',
                background: 'blue'
            }}><Link to="/auth/signup" style={{color: 'white', textDecoration: "none"}}>회원가입</Link></button>
            <h3 onClick = {()=>{
                removeCookies("accessToken");
            }
            }>DMZ project</h3>
            <p onClick={ ()=> {
                const accessToken = getCookies("accessToken");
                console.log(accessToken);
                axios.get("http://localhost:8080/member/me")
                    .then((response) => {
                        console.log(response.data);
                    })
                    .catch((response) => { console.log('Error!') });
            }}>너도 오던가</p>
        </div>
    )
}

export { Main };