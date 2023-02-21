import React, { useState, useEffect } from 'react';
import {useMediaQuery} from "react-responsive";
import '../css/FindId.module.css';
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";
import Layout from "./layout/Layout";

function FindPw(props){

    const navigate = useNavigate();

    const [Id, setId] = useState("");
    const [Name, setName] = useState("");
    const [Phone, setPhone] = useState("");
    const [SendNum, setSendNum] = useState("");      //서버가 보낸 인증번호
    const [ConfirmNum, setConfirmNum] = useState(""); //사용자가 입력한 인증번호

    const onIdHandler = (event) => {
        setId(event.currentTarget.value);
    }
    const onNameHandler = (event) => {
        setName(event.currentTarget.value);
    }
    const onPhoneHandler = (event) => {
        setPhone(event.currentTarget.value);
    }
    const onSendHandler = (event) => {
        //1. 먼저 회원인지 아닌 지 체크(=데베에 데이터 있는 지 체크)
        //2-1. 회원이 아니라면, 경고창 띄우기
        axios.post("http://localhost:8080/auth/findId", {phonenumber : Phone},
            {
                withCredentials : true,
                headers : {"Content-Type": 'application/json'}
            })
            .then((result)=>{
                console.log(result);
            })
            .catch((response)=>{console.log('이상하다')})

    }
    const onConfirmNumHandler = (event) => {
        setConfirmNum(event.currentTarget.value);
    }
    const onCheckHandler = (event) => {
        //보낸 인증번호와 입력된 인증 번호가 일치하는 지 확인
        axios.post("http://localhost:8080/auth/findId/veritfySMS",
            {code : ConfirmNum, phonenumber : Phone},
            {
                withCredentials : true,
                headers : {"Content-Type": 'application/json'}
            })
            .then((result)=> {
                console.log(result);

                if(result == false) alert('인증번호 틀림');
                else{//인증번호 맞음->아이디 알려주는 창으로 ㄱ
                    console.log('인증번호 일치');
                    //<Link to={'/auth/showid/${Phone}'}></Link>
                    navigate('/auth/showpw/'+result.data.email);
                }
            })

    }

    return(
        <Layout>
            <div style={{width: "100%", height: "100%"}}>
                {/*<Nav></Nav>*/}
                <h2 style={{borderBottom : '2px solid black',margin : '30px'}}>비밀번호 재설정</h2>
                <div className="find" style={{
                    display: 'flex', justifyContent: 'center', alignItems: 'center',
                    width: '100%', height: '100vh'
                }}>
                    <div className="findId" style={{ height: "90%" ,display: 'flex', flexDirection: 'column', alignItems: 'center', paddingTop: "3%"}}>
                        <div>
                            <h4>이메일</h4>
                            <input className='inputBox' type='id' value={Id} placeholder = '입력해주세요' onChange={onIdHandler}/>
                        </div>
                        <div>
                            <h4>이름</h4>
                            <input className='inputBox' type='name' value={Name} placeholder = '입력해주세요' onChange={onNameHandler}/>
                        </div>
                        <div>
                            <h4>휴대전화</h4>
                            <input className='inputBox' type='phone' value={Phone} placeholder = '입력해주세요' onChange={onPhoneHandler}/>
                        </div>
                        <button className='btn' onClick={onSendHandler}>인증 번호 받기</button>
                        <div>
                            <h4>인증 번호 입력</h4>
                            <input className='inputBox' type='confirmNum' value={ConfirmNum} placeholder = '입력해주세요' onChange={onConfirmNumHandler}/>
                        </div>
                        <button className='btn' onClick={onCheckHandler}>인증 번호 확인</button>
                        {/*<div>
                            <button style={{marginTop: "20px", width: "100px",
                                height: "30px", background: "black", color: "white", borderRadius: "30px"}}>
                                <Link to="/auth/login" style={{color: "white"}}>로그인 하기</Link>
                            </button>
                        </div>*/}
                    </div>
                </div>
            </div>
        </Layout>
    )
}

function Nav(){
    return(
        <div className="nav">
            <Link to="/">
                <h2 style={{color: "white"}}>DMZ - find password</h2>
            </Link>
        </div>
    )
}

export {FindPw};