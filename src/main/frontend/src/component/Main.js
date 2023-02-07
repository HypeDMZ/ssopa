import React , { useState, useEffect } from "react";
import { Link, Route, Routes , BrowserRouter} from 'react-router-dom';
import "../css/Main.css";
import logo from"../img/logo.png";

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
            <h3>DMZ project</h3>
            <p>너도 오던가</p>
        </div>
    )
}

export { Main };