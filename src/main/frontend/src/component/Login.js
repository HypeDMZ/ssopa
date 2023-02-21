import React, { useState, useEffect } from 'react';
import {Link, useNavigate} from "react-router-dom";
import axios, {get} from 'axios';
import Layout from "./layout/Layout";
import {setCookie,getCookie,removeCookie} from "../function/cookie";
import styled from '../css/Login.module.css'
import {useMediaQuery} from "react-responsive";

function Login(props){
    const navigate = useNavigate();

    const [saveId, setSaveId] = useState(false);
    let [아이디, 아이디변경] = useState('');
    let [비밀번호, 비밀번호변경] = useState('');
    let [loginDataJSON, loginDataJSONChange] = useState([]);
    let loginData = new Object();

    useEffect( () => {
        loginData.email = 아이디;
        loginData.password = 비밀번호;
        loginDataJSONChange(JSON.stringify(loginData));
    },[아이디,비밀번호]);

    useEffect(()=>{
        if(getCookie('id')!==undefined){
            아이디변경(getCookie('id'));
        }
    },[]);

    const onLogin = () => {
        console.log(loginDataJSON);
        axios.post("https://ssopa02.com/api/auth/login",
            loginDataJSON,
            {
                withCredentials : true,
                headers : {"Content-Type": 'application/json'}
            })
            .then((response) => {
                console.log(response.data);
                axios.defaults.headers.common["Authorization"] = `Bearer ${response.data.data.accessToken}`;
                console.log(axios.defaults.headers.common["Authorization"]);
                console.log(`Bearer ${response.data.data.refreshToken}`);

                setCookie('token',
                    {accessToken: `Bearer ${response.data.data.accessToken}`
                        ,refreshToken: `Bearer ${response.data.data.refreshToken}`},
                    {
                        path: "/"
                    });

                console.log(saveId)
                if(saveId == true){
                    setCookie('id', 아이디,{path:"/auth/login"});
                    console.log(getCookie('id'));
                }
                else if(saveId == false && getCookie('id') != undefined){
                    removeCookie('id',{path:"/auth/login"});
                    console.log(getCookie('id'));
                }
                console.log(getCookie('token'));
                alert("로그인 완료");

                navigate("/Post");
            })
            .catch((response) => { console.log('Error!') });
    }


    return (
        <Layout component={<TopButton navigate={navigate}/>}>
            <LoginMainPC 아이디={아이디} 아이디변경={아이디변경} 비밀번호={비밀번호} 비밀번호변경={비밀번호변경} onLogin={onLogin} saveId={saveId} setSaveId={setSaveId}/>
        </Layout>
    )
}
const TopButton = (props) => {
    return
        // <div>
        //     <button className={styled.login_nav_button} style={{left:"67.56%", top:"3.7%"}}
        //             onClick={()=>{props.navigate("/auth/findId")}}>아이디 찾기</button>
        //     <button className={styled.login_nav_button} style={{left:"84.23%", top:"3.7%"}}
        //             onClick={()=>{props.navigate("/auth/findpw")}}>비밀번호 찾기</button>
        // </div>
}
const LoginMainPC = (props) => {
    return(
        <>
            <div className={styled.login_main}/>
            <div className={styled.login_dark}/>
            <div className={styled.login_title}><p style ={{display : "inline"}}>SSOPA</p>와 함께하는 고등학교 생활!</div>

            <div className={styled.login_option} style={{top:"38.37%" , left:"30%"}}>email</div>
            <div className={styled.login_option} style={{top:"46.87%" , left:"28%"}}>password</div>
            <input type='text' value = {props.아이디} className={styled.login_input} style={{left: "39.1%", top: "38.28%"}}
                   onChange={ (e)=>{props.아이디변경(e.target.value);}}/>

            <input type='password' value = {props.비밀번호} className={styled.login_input} style={{left: "39.1%", top: "47.55%"}}
                   onChange={ (e)=>{props.비밀번호변경(e.target.value);}}/>

            <div className={styled.login_option} style={{top:"52%" , left:"40%"}}>
                <input type="checkbox" style={{display : "inline"}}
                checked={props.saveId} onChange={(event) => {
                        props.setSaveId(event.target.checked);
                    }
                }/><p style={{fontSize:"5px"}}>아이디 저장</p>
            </div>

            <div className={styled.login_option} style={{top:"52%" , left:"60%"}}>
                <Link to="/auth/findId" style={{fontSize : "5px", color: "#CD5600"}}> 아이디찾기  </Link>
                <p style={{fontSize:"10px"}}>/</p>
                <Link to="/auth/findPw" style={{fontSize : "5px", color: "#CD5600"}}> 비밀번호찾기  </Link>
            </div>


            <button className={styled.login_button} onClick={ ()=>{
                {props.onLogin()}
            }} style={{fontSize : "25px", color : "white"}}>Login</button>
            <Link to="/auth/signup" className={styled.login_signup_nav}> <p style={{display : "inline", marginRight : "10px"}}>아니 아직 회원이 아니라고?</p> 회원가입 </Link>
        </>
    )
}

export { Login };