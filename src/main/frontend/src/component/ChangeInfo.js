import styled from '../css/ChangeInfo.module.css'
import basicProfile from '../img/basicProfile.png';
import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";

function ChangeInfo(){

    const {option} = useParams();
    const [Change, setChange] = useState("");
    const [Before, setBefore] = useState("");
    const [New, setNew] = useState("");
    const [Confirm, setConfirm] = useState("");
    const [Email, setEmail] = useState("");
    const [Type, setType] = useState("");

    let [changeDataJSON, changeDataJSONChange] = useState([]);
    let changeData = new Object();
    let [Url, setUrl] = useState("");
    const navi = useNavigate();

    useEffect(()=>{
        if(option == "email"){
            setChange('이메일');
            setType("email");
        }
        else if(option == "password"){
            setChange('비밀번호');
            setType("password");
        }
        else if(option == "nickname"){
            setChange('닉네임');
            setType("text");
        }
        else if(option == "school"){
            setChange('학교');
            setType("text");
        }
    },[])

    const onBeforeHandler = (event) => {
        //사용자가 입력한 인증 번호
        setBefore(event.currentTarget.value);
    }
    const onNewHandler = (event) => {
        //사용자가 입력한 인증 번호
        setNew(event.currentTarget.value);
    }
    const onConfirmHandler = (event) => {
        //새로운 비밀번호 확인
        setConfirm(event.currentTarget.value);
    }
    const onEmailHandler = (event) => {
        //이메일 입력
        setEmail(event.currentTarget.value);
    }
    const onCheckHandler = (event) => {
        console.log(axios.defaults.headers.common["Authorization"]);

        if(option == "email"){

        }
        else if(option == "password"){
            changeData.email = Email;
            changeData.exPassword = Before;
            changeData.newPassword = New;

            setUrl("/api/member/password");

            console.log(changeData);
        }
        else if(option == "nickname"){

        }
        else if(option == "school"){

        }

        changeDataJSONChange(JSON.stringify(changeData));

        axios.post(Url, changeDataJSON, {
            withCredentials : true,
            headers : {"Content-Type": 'application/json'}
        })
            .then((result)=> {
                alert('변경 성공!');
                console.log(result.data);
                navi("/");
            })
            .catch((response)=>{console.log('이상하다')})
    }
    
    return(
        <div className={styled.changeInfo_container}>
            <div className={styled.changeInfo_nav}></div>
            <div className={styled.changeInfo_main_container}>
                <div className={styled.changeInfo_main_content}>
                    <div className={styled.changeInfo_main_nav}>
                        <p style={{
                            color: "#FFFFFF", fontWeight: "bold", fontSize: "28px", marginLeft: "20px"
                        }}>내 정보 변경-{Change}</p>
                    </div>
                    {
                        option == "password"
                            ? <div className={styled.changeInfo_info_detail}>
                                <p style={{color:"#E87D30", fontWeight: "700"}}>이메일</p>
                                <div>
                                    <input className={styled.changeInfo_input} type="text" placeholder = '입력해주세요' onChange={onEmailHandler}/>
                                </div>
                            </div>
                            : null
                    }
                    <div className={styled.changeInfo_info}>
                        <div className={styled.changeInfo_info_detail}>
                            <p style={{color:"#E87D30", fontWeight: "700"}}>현재 {Change}</p>
                            <div>
                                <input className={styled.changeInfo_input} type={Type} value={Before} placeholder = '입력해주세요' onChange={onBeforeHandler}/>
                            </div>
                        </div>

                        <div className={styled.changeInfo_info_detail}>
                            <p style={{color:"#E87D30", fontWeight: "700"}}>새로운 {Change}</p>
                            <div>
                                <input className={styled.changeInfo_input} type={Type} value={New} placeholder = '입력해주세요' onChange={onNewHandler}/>
                            </div>
                        </div>

                        {
                            option == "password"
                                ? <div className={styled.changeInfo_info_detail}>
                                    <p style={{color:"#E87D30", fontWeight: "700"}}>새로운 {Change} 확인</p>
                                    <div>
                                        <input className={styled.changeInfo_input} type={Type} value={Confirm} placeholder = '입력해주세요' onChange={onConfirmHandler}/>
                                    </div>
                                </div>
                                : <div className={styled.changeInfo_info_detail}></div>
                        }

                        <div className={styled.changeInfo_info_btn_box}>
                            <button className={styled.changeInfo_info_btn} onClick={onCheckHandler}>확인</button>
                        </div>
                        <div className={styled.changeInfo_info_btn_box}>
                            <button className={styled.changeInfo_info_btn}>취소</button>
                        </div>
                        <p style={{textAlign: "center", color: "#FDD2B3"}}>ssopa02.com</p>
                    </div>

                </div>
            </div>
        </div>
    )
}

export {ChangeInfo};