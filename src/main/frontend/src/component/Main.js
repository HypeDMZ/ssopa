import React , { useState, useEffect } from "react";
import { Link, Route, Routes , BrowserRouter} from 'react-router-dom';
import {Container, Row, Col} from 'react-bootstrap';
import "../css/Main.css";
import logo from"../img/logo.png";
import { useBootstrapBreakpoints } from "react-bootstrap/esm/ThemeProvider";

function Main(props){

    return (

        <div className="container">
            <div className ="leftPage">
                <div className ="video">
                    <iframe className ="map" src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3167.4238737627243!2d127.12666081524173!3d37.45071277981981!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x357ca8a401f3127b%3A0x39d64e7ee201cd15!2z6rCA7LKc64yA7ZWZ6rWQ!5e0!3m2!1sko!2skr!4v1676188840683!5m2!1sko!2skr"></iframe>

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