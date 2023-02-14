import React, { useState, useEffect } from 'react';
import {Link} from "react-router-dom";
import axios, {get} from 'axios';
import Layout from "./layout/Layout";
import {setCookie,getCookie,removeCookie} from "../function/cookie";

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
    return (
        <Layout>
            <h4>홈</h4>
            <input type={"text"} onChange={ (e)=>{
                아이디변경(e.target.value);
            }}></input><br/>
            <input type={"password"} onChange={ (e)=>{
                비밀번호변경(e.target.value);
            }}></input><br/>
            <button onClick={ ()=>{
                onLogin();
            }}>로그인</button>
        </Layout>
    )
}

export { Login };