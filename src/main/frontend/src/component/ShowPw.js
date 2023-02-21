import {Link, useNavigate, useParams} from "react-router-dom";
import React, {useState} from "react";
import styled from "../css/FindId.module.css";
import Layout from "./layout/Layout";
import axios from "axios";


function ShowPw(props){

    const navigate = useNavigate();
    const {email} = useParams();
    const [기존비밀번호, 기존비밀번호설정] = useState('');
    const [새비밀번호, 비밀번호설정] = useState('');
    const [비밀번호확인, 확인설정] = useState('');

    const onPwHandler = (event) => {
        비밀번호설정(event.currentTarget.value);
    }
    const onExPwHandler = (event) => {
        기존비밀번호설정(event.currentTarget.value);
    }
    const onConfirmPwHandler = (event) => {
        확인설정(event.currentTarget.value);
    }
    const onChangePwHandler = (event) => {
        //console.log(email,새비밀번호, 비밀번호확인);
        axios.post("http://localhost:8080/auth/resetpassword"
            , {email: email, password: 새비밀번호, passwordConfirm: 비밀번호확인},
            {
                withCredentials : true,
                headers : {"Content-Type": 'application/json'}
            })
            .then((result)=>{
                console.log(result.data);
                //성공시 알람 띄우기
                alert('비밀번호 바꾸기 성공!');
                navigate('/auth/login');
            })
            .catch((response)=>{console.log(response)})
    }
    return(
        <Layout>
            <div style={{width: "100%", height: "100%"}}>
                {/*<Nav></Nav>*/}

                <h2 style={{borderBottom : '2px solid black',margin : '30px'}}>비밀번호 재설정</h2>
                <div className={styled.find} style={{
                    display: 'flex', justifyContent: 'center', alignItems: 'center',
                    width: '100%', height: '100vh'
                }}>
                    <div className={styled.findId} style={{height: "60%" ,display: 'flex', flexDirection: 'column',  padding: "10%"}}>
                        <div>
                            <h4>새로운 비밀번호</h4>
                            <input className={styled.inputBox} type='password' value={새비밀번호} placeholder = '입력해주세요' onChange={onPwHandler}/>
                        </div>
                        <div>
                            <h4>새로운 비밀번호 확인</h4>
                            <input className={styled.inputBox} type='password' value={비밀번호확인} placeholder = '입력해주세요' onChange={onConfirmPwHandler}/>
                        </div>
                        <div>
                            <button style={{marginTop: "20px", width: "120px",
                                height: "30px", background: "black", color: "white", borderRadius: "30px"}
                                }
                                onClick={onChangePwHandler}>
                               비밀번호 재설정
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
        <div className={styled.nav}>
            <Link to="/">
                <h2 style={{color: "white"}}>DMZ</h2>
            </Link>
        </div>
    )
}

export {ShowPw}