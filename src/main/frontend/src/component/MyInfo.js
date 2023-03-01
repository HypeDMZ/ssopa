import styled from '../css/MyInfo.module.css'
import basicProfile from '../img/basicProfile.png';
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {tokenRefreshing} from "../function/tokenRefreshing";
import axios from "axios";

function MyInfo(){
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [school, setSchool] = useState("");
    const [nickName, setNickName] = useState("");
    const [option, setOption] = useState("");
    const navi = useNavigate();

    /*재 랜더링 할때마다 server에서 최신 post를 가져온다.`/api/post/load/${room}/${page}`*/
    useEffect(() => {
        tokenRefreshing().then(() => {
            axios.get('/api/member/me',
                { withCredentials: true})
                .then((response) => {
                    //console.log(response.data.data);
                    setName(response.data.data.name);
                    setNickName(response.data.data.nickname);
                    setEmail(response.data.data.email);
                })
                .catch((response) => { console.log(response) });
        });
    }, [nickName], [name]);

    const onNickNameHandler = (e) => {
        //console.log(axios.defaults.headers.common["Authorization"]);
        axios.post("/api/member/nickname", {
            withCredentials : true,
            headers : {"Content-Type": 'application/json'}
        })
            .then((result)=> {
                alert('닉네임 변경 성공!');
                console.log(result.data);
                setNickName(result.data.data.nickname);
            })
            .catch((response)=>{console.log('이상하다')})
    }

    return(
        <div className={styled.myInfo_container}>
            <div className={styled.myInfo_nav}></div>
            <div className={styled.myInfo_main_container}>
                <div className={styled.myInfo_main_content}>
                    <div className={styled.myInfo_main_nav}>
                        <p style={{
                            color: "#FFFFFF", fontWeight: "bold", fontSize: "28px", marginLeft: "20px"
                        }}>내 정보</p>
                    </div>
                    <div className={styled.myInfo_profile}>
                        <img src={basicProfile} style={{
                            width: "16%", height: "80%", marginTop: "2%", marginLeft: "2%"
                        }}/>

                        <div className={styled.myInfo_name}>
                            <p>{name}({school})</p>
                            <p style={{color: "#FFAE74"}}>{nickName}</p>
                        </div>

                        <button className={styled.myInfo_logout_btn} onClick={() => {navi("/")}}>로그아웃</button>
                    </div>
                    <div className={styled.myInfo_info}>
                        <div className={styled.myInfo_info_detail}>
                            <p style={{color:"#E87D30", fontWeight: "700"}}>계정 관리</p>
                            <div className={styled.myInfo_info_btn_box}>
                                <button className={styled.myInfo_info_btn}>학교 인증하기</button>
                                <button className={styled.myInfo_info_btn} onClick={() => {
                                    navi('/member/change/email');
                                }}>이메일 변경하기</button>
                                <button className={styled.myInfo_info_btn} onClick={() => {
                                    navi('/member/change/password');
                                }}>비밀번호 변경하기</button>
                            </div>
                        </div>

                        <div className={styled.myInfo_info_detail}>
                            <p style={{color:"#E87D30", fontWeight: "700"}}>커뮤니티 관리</p>
                            <div className={styled.myInfo_info_btn_box}>
                                <button className={styled.myInfo_info_btn} onClick={onNickNameHandler}>닉네임 변경하기</button>
                            </div>
                        </div>

                        <div className={styled.myInfo_info_detail}>
                            <p style={{color:"#E87D30", fontWeight: "700"}}>기타</p>
                            <div className={styled.myInfo_info_btn_box}>
                                <button className={styled.myInfo_info_btn} onClick={() => {
                                    navi('/member/change/school');
                                }}>학교 변경하기</button>
                                <button className={styled.myInfo_info_btn}>회원 탈퇴하기</button>
                            </div>
                        </div>

                        <p style={{textAlign: "center", color: "#FDD2B3"}}>ssopa02.com</p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export {MyInfo};