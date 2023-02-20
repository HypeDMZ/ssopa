import React, { useState, useEffect } from 'react';
import {useMediaQuery} from "react-responsive";
import { Link , useNavigate} from 'react-router-dom';
import '../css/FindId.css';
import  './layout/Layout'
import axios from "axios";
import Layout from "./layout/Layout";

/*export const Mobile = ({ children }) => {
    const isMobile = useMediaQuery({
        query: "(max-width:768px)"
    });
    return <>{isMobile && children}</>
}

export const Pc = ({ children }) => {
    const isPc = useMediaQuery({
        query: "(min-width:769px)"
    });
    return <>{isPc && children}</>
}*/

function FindId(props) {

    const navigate = useNavigate();
    const [Name, setName] = useState("");
    const [Phone, setPhone] = useState("");
    const [ConfirmNum, setConfirmNum] = useState(""); //사용자가 입력한 인증번호

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
        //2-2. 회원이라면, 위에서 입력된 이름, 휴대폰 번호에 해당하는 사람한테 인증번호 보내줘야함

        //인증번호 설정 어케할 지?

        //보내는 부분

    }
    const onConfirmNumHandler = (event) => {
        //사용자가 입력한 인증 번호
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
                    navigate('/auth/showid/'+result.data.email);
                }
            })


    }

    return(
        /*<div>
            <Nav></Nav>
            <h2 style={{borderBottom : '2px solid black',margin : '30px'}}>아이디 찾기</h2>
            <div className="find" style={{
                display: 'flex', justifyContent: 'center', alignItems: 'center',
                width: '100%', height: '100vh'
            }}>
                <div className="findId" style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', paddingTop: "3%"}}>
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
                    <div>
                        <button style={{marginTop: "20px", width: "100px",
                            height: "30px", background: "black", color: "white", borderRadius: "30px"}}>
                            <Link to="/auth/findpw" style={{color: "white"}}>비밀번호 찾기</Link>
                        </button>
                    </div>

                </div>
            </div>
        </div>*/
        <Layout>
            <div style={{width: "100%", height: "100%"}}>
                {/*<Nav></Nav>*/}
                <h2 style={{borderBottom : '2px solid black',margin : '30px'}}>아이디 찾기</h2>
                <div className="find" style={{
                    display: 'flex', justifyContent: 'center', alignItems: 'center',
                    width: '100%', height: '100vh'
                }}>
                    <div className="findId" style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', paddingTop: "3%"}}>
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
                        <div>
                            <button style={{marginTop: "20px", width: "100px",
                                height: "30px", background: "black", color: "white", borderRadius: "30px"}}>
                                <Link to="/auth/findpw" style={{color: "white"}}>비밀번호 찾기</Link>
                            </button>
                        </div>

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
                <h2 style={{color: "white"}}>DMZ - find id</h2>
            </Link>
        </div>
    )
}

export {FindId};


