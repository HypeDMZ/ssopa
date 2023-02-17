import React , { useState, useEffect } from "react";
import { Link, Route, Routes , BrowserRouter} from 'react-router-dom';
import {Container, Row, Col} from 'react-bootstrap';
import "../css/Main.css";
import logo from"../img/logo.png";
import { useBootstrapBreakpoints } from "react-bootstrap/esm/ThemeProvider";
import {Post_Lunch} from "./Post_Menu/Post_Lunch";
import {Post} from "./Post";

function Main(props){

    return (

        <div className="container">
            <div className ="leftPage">
                <div className ="video">
                </div>
            </div>

            <div className ="rightPage">
                <div className ="icon"> </div>

                <div className="Login">호
                    <button> <Link to ="/auth/login" >  <p>로그인</p> </Link> </button>
                    <button> <Link to= "/auth/signup"> <p>DMZ 회원가입</p> </Link></button>
                    <Link to ="/auth/findid" className ="findid"> 아이디/비밀번호 찾기</Link>
                </div>

                <div className ="area">
                    <div>실시간 검색어 OR 광고업체 링크/나중에 a.map머시기로 하면될듯</div>
                    <ul>
                        <li> <a href ="https://cafe.naver.com/suhui"> 수만휘 </a> </li>
                        <li> <a href ="http://www.megastudy.net/ "> 메가스터디 </a> </li>
                        <li> <a href ="https://www.etoos.com/home/default.asp"> 이투스 </a> </li>
                        <li> <a href="#"> 스터디카페 </a> </li>
                    </ul>
                </div>

                <p className ="saying">배우고 때로 익히면 또한 기쁘지 아니한가 '學而時習之 不亦說乎' </p>
            </div>
        </div>
    )
}

export { Main };