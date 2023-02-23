import React, {useEffect, useState} from "react";
import Layout from '../css/layout/Layout.css'
import styled from '../css/Post.module.css'
import {Link} from 'react-router-dom';
import axios from 'axios'
function Post()
{
    let [post, changePost] = useState();


    /*재 랜더링 할때마다 server에서 최신 post를 가져온다.*/
    useEffect( () => {
        let recentPost = axios.get("url")
        .then((data)=>{
            post = data.data.data;
            changePost(post);
        })
    },[post])


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
                        <div>📋 오픈채팅방</div>
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
                    <div className={styled.post_main_search}>
                        <div style={{display : "inline", width : "10%", height : "90%", fontSize : "30px", paddingTop : "5px"}}>🔍</div>
                        <input className={styled.post_input} style={{width : "70%", height : "70%", border : "none", marginLeft : "10px"}} placeholder= "전체 게시판의 글을 입력해보세요"/>
                        <button onClick={()=>{}}>검색</button>
                    </div>
                    <div className={styled.post_main_contents}>
                        <div className={styled.post_main_nav}>자유게시판  <button style={{float : "right", borderRadius : "10px", backgroundColor : "#F2B284"}}
                        onCLick>글쓰기</button></div>
                        <div className={styled.post_main_post}>
                            {
                                /*[0,1,2...]말고 post를 받아와서 사용하면됨.*/
                                [0,1,2,3,4,5,6].map((a,i)=>{
                                    return(
                                        <div className={styled.post_post}>
                                            <p>Title</p>
                                            <p>Contents</p>
                                            <button onClick={()=>{

                                            }
                                            }>Delete</button>
                                        </div>
                                    )
                                })
                            }
                        </div>
                    </div>
                </div>
                <div className={styled.post_main_right}>
                    <div className={styled.post_main_hotTopic}>
                        <div className={styled.post_hotTopic_nav}>Hot Topic</div>
                    </div>

                    <div className={styled.post_main_lunch}>
                        <div className={styled.post_Lunch_nav}>급식 게시판</div>
                    </div>

                </div>
            </div>
        </div>
    )
}

export {Post};