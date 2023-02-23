import React, {useState} from "react";
import Layout from '../css/layout/Layout.css'
import styled from '../css/AfterSearchingPost.module.css'
import {Link} from 'react-router-dom';
function AfterSearchingPost()
{
    return(
        <div className={styled.post_container}>
            <div className={styled.post_my}>
                <div style={{margin : "10px", borderBottom : "solid 1.5px #F2B284", padding : "2px", color : "#F2B284"}}>profile</div>
                <div className={styled.post_profile}>
                    <div className={styled.post_profile_img}></div>
                    <div className={styled.post_profile_info}>
                        <p style={{textAlign : "center"}}>성이름(대전고)</p>
                        <button style={{width: "80%", height : "30px", borderRadius : "10px", backgroundColor : "#FBD0B2"}}>내 정보</button>
                    </div>
                    <div className={styled.post_profile_catagory}>
                        <div>✍️ 내가 쓴 글</div>
                        <div>📖 댓글 단 글</div>
                        <div>📋 책갈피</div>
                    </div>
                </div>
                <div className={styled.post_scoreGraph}>
                    <p>내 성적 그래프</p>
                    <div className={styled.post_score}></div>
                </div>
                <Link to ="/" style={{color : "black",borderRadius : "5px", textDecoration : "none", border : "none", backgroundColor : "#FBD0B2", marginTop : "25px"}} onClick={()=>{}}>로그아웃</Link>
            </div>

            <div className={styled.post_nav}></div>
            <div className={styled.post_main_container}>

                <div className={styled.post_main_left}>
                    <div className={styled.post_main_contents}>
                        <div className={styled.post_main_nav}> <p>내가 쓴 글 (1) </p></div>
                        <div className={styled.post_myStory}>
                                <div className={styled.post__profile}>
                                    <div className={styled.post_profile__img}></div>
                                    <div className={styled.post_name}>
                                        <p style={{display : "block", fontSize : "13px"}}>익명</p>
                                        <p style={{fontSize : "13px"}}>대전고등학교 </p>
                                        <p style={{border : "solid 2px black", fontSize : "13px"}}> 3분전</p>
                                    </div>
                                    <div className={styled.post_112}>
                                        <p style={{color : "#F2B284"}}>[자유게시판]</p>
                                    </div>
                                </div>


                                <div className={styled.post_contents}>
                                    <div className={styled.post_contents_contents}>
                                        <button className={styled.post_contents_title}>아 졸리다...ㅜ,.ㅜ</button>
                                        <button className={styled.post_contents_comment}>내일 뭐하지?</button>
                                    </div>
                                    <div className={styled.post_good}>
                                        <button>👍 0 </button>
                                        <button>📖 0 </button>
                                    </div>
                                </div>
                            </div>
                    </div>
                </div>


                <div className={styled.post_main_right}>
                    <div className={styled.post_main_hotTopic}>
                        <div className={styled.post_hotTopic_nav}>Hot Topic</div>
                    </div>
                </div>

            </div>
        </div>
    )
}

export {AfterSearchingPost};