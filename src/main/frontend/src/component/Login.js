import React, { useState, useEffect } from 'react';
import {Link} from "react-router-dom";
import axios, {get} from 'axios';
import Layout from "./layout/Layout";
import {setCookie,getCookie,removeCookie} from "../function/cookie";
import "../css/Login.css";
import {useMediaQuery} from "react-responsive";

function Login(props){
    let [아이디, 아이디변경] = useState('');
    let [비밀번호, 비밀번호변경] = useState('');
    let [loginDataJSON, loginDataJSONChange] = useState({});
    let loginData = new Object();

    useEffect( () => {
        loginData.email = 아이디;
        loginData.password = 비밀번호;
        loginDataJSONChange(JSON.stringify(loginData));
    },[아이디,비밀번호]);

    const Min = () =>{
        const isMin = useMediaQuery({ minWidth: 768});
        {/*null 자리에 MoblieLogin */}
        return isMin ? <LoginMainPC/> : null;
    }


    const onLogin = () => {
        console.log(loginDataJSON);
        axios.post("http://localhost:8080/auth/login",
            loginDataJSON,
            {
                withCredentials : true,
                headers : {"Content-Type": 'application/json'}
            })
            .then((response) => {
                console.log(response.data);
                axios.defaults.headers.common["Authorization"] = `Bearer ${response.data.accessToken}`;
                console.log(axios.defaults.headers.common["Authorization"]);
                console.log(`Bearer ${response.data.refreshToken}`);

                setCookie('token',
                    {accessToken: `Bearer ${response.data.accessToken}`
                        ,refreshToken: `Bearer ${response.data.refreshToken}`},
                    {
                        path: "/"
                    });

                console.log(getCookie('token'));

                removeCookie('token')

                console.log(getCookie('token'));

                alert("로그인 완료");
            })
            .catch((response) => { console.log('Error!') });
    }

    const LoginMainPC = () => {
        return(
            <>
                <div className={"login-main"}/>
                <div className={"login-dark"}/>
                <div className={"login-title"}>행복해지는 길</div>
                <div className={"login-option"} style={{top:"38.37%" , left:"30%"}}>email</div>
                <div className={"login-option"} style={{top:"46.87%" , left:"28%"}}>password</div>
                <input type={"text"} className={"login-input"} style={{left: "39.1%", top: "38.28%"}}
                       onChange={ (e)=>{아이디변경(e.target.value);}}/>
                <input type={"password"} className={"login-input"} style={{left: "39.1%", top: "47.55%"}}
                       onChange={ (e)=>{비밀번호변경(e.target.value);}}/>
                <button className={"login-button"} onClick={ ()=>{
                    onLogin();
                }}>로그인</button>
                <Link to="/auth/signup" className={"login-signup-nav"}>아니 아직 회원이 아니라고?</Link>
            </>
        )
    }

    return (
        <Layout>
            <Min/>
        </Layout>
    )
}
export { Login };