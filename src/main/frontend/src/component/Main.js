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
                <p style ={{fontSize : "20px" , color : "black", marginBottom : "-60px", marginLeft : "-20px"}}>전국 고등학생들의 커뮤니티</p>
               <p >SSOPA</p>
            </div>

            <div className ="rightPage">
                <div className="Login">
                    <div className="icon"> </div>
                    <p style={{fontSize : "40px", color : "white"}} className="SSOPA">SSOPA</p>

                    <button style = {{backgroundColor : "#FFF3D3"}}> <Link to ="/auth/login" >  <p>로그인</p> </Link> </button>
                    <button style= {{backgroundColor : "#E87D30"}}> <Link to= "/auth/signup"> <p>DMZ 회원가입</p> </Link></button>
                    <Link to ="/auth/findid" className ="findid" style={{float : "right", textDecoration : "none"}}> 아이디/비밀번호 찾기</Link>
                </div>

                <div style={{width : "100%", height : "10px", backgroundColor :"white"}}></div>

                <div className ="area">
                    <div className ="around">
                            <h4>인강순위</h4>
                        <div className="adver">
                            <ul>
                                <li> <a href ="https://cafe.naver.com/suhui"> 수만휘 </a> </li>
                                <li> <a href ="http://www.megastudy.net/ "> 메가스터디 </a> </li>
                                <li> <a href ="https://www.etoos.com/home/default.asp"> 이투스 </a> </li>
                                <li> <a href="#"> 스터디카페 </a> </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export { Main };