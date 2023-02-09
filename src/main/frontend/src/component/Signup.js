import React, { useState, useEffect } from 'react';
import axios from "axios";
function Signup(props){
    let [이메일, 이메일변경] = useState('');
    let [비밀번호, 비밀번호변경] = useState('');
    let [이름, 이름변경] = useState('');
    let [SignUpDataJSON, SignUpDataJSONChange] = useState({});
    let SignUpData = new Object();

    useEffect(()=>{
        SignUpData.email = 이메일;
        SignUpData.nickname = 이름;
        SignUpData.password = 비밀번호;
        SignUpDataJSONChange(JSON.stringify(SignUpData));
    },[이메일, 비밀번호, 이름]);
    return (
        <>
            <div>
                <h4>회원가입</h4>
                <span>닉네임<input type={"text"} value={이름} onChange={ (e)=>{
                    이름변경(e.target.value);
                }}></input></span><br/>
                <span>이메일<input type={"text"} value={이메일} onChange={ (e)=>{
                    이메일변경(e.target.value);
                }}></input></span><br/>
                <span>비밀번호<input type={"password"} value={비밀번호} onChange={ (e)=>{
                    비밀번호변경(e.target.value);
                }}></input></span><br/>
            </div>
            <div>
                <button onClick={ ()=>{
                    console.log(SignUpDataJSON);
                    axios.post("http://localhost:8080/auth/signup",
                        SignUpDataJSON,
                        {
                            withCredentials : true,
                            headers : {"Content-Type": 'application/json'}
                        })
                        .then((response) => { console.log(response.data); })
                        .catch((response) => { console.log('Error!') });
                }}>회원가입</button>
            </div>
        </>
    )
}

export {Signup};