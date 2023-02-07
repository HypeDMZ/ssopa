import React, { useState, useEffect } from 'react';
import axios from "axios";
function Signup(props){
    let [이메일, 이메일변경] = useState('');
    let [비밀번호, 비밀번호변경] = useState('');
    let [이름, 이름변경] = useState('');
    let [SignUpDataJSON, SignUpDataJSONChange] = useState({});


    return (
        <div>
            <h4>회원가입</h4>
            <span>닉네임<input type={"text"} onChange={ (e)=>{
                이름변경(e.target.value);
            }}></input></span><br/>
            <span>이메일<input type={"text"} onChange={ (e)=>{
                이메일변경(e.target.value);
            }}></input></span><br/>
            <span>비밀번호<input type={"password"} onChange={ (e)=>{
                비밀번호변경(e.target.value);
            }}></input></span><br/>
            <button onClick={ ()=>{
                let SignUpData = new Object();
                SignUpData.email = 이메일;
                SignUpData.nickname = 이름;
                SignUpData.password = 비밀번호;

                SignUpDataJSONChange(JSON.stringify(SignUpData));
                console.log(SignUpDataJSON);

                axios.post("http://13.125.69.242:8080/auth/signup",
                    SignUpDataJSON,
                    {
                        withCredentials : true,
                        headers : {"Content-Type": 'application/json'}
                    })
                    .then((response) => { console.log(response.data); })
                    .catch((response) => { console.log('Error!') });
            }}>회원가입</button>
        </div>
    )
}

export {Signup};