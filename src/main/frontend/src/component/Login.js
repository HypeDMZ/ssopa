import React, { useState, useEffect } from 'react';
import {Link} from "react-router-dom";
import axios, {get} from 'axios';
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

                        setCookie('token', axios.defaults.headers.common["Authorization"],
                            {
                                path: "/"
                            });

                        console.log(getCookie('token'));

                        removeCookie('token')

                        console.log(getCookie('token'));
                    })
                    .catch((response) => { console.log('Error!') });

            }}>로그인</button>
        </div>
    )
}

export { Login };