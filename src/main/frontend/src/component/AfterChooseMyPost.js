import styled from "../css/AfterChooseMyPost.module.css";
import {Link, useLocation, useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {tokenRefreshing} from "../function/tokenRefreshing";
import axios from "axios";
import { BsFillGearFill } from 'react-icons/bs';

function AfterChooseMyPost(){
    let [mypost, modify] = useState({})
    let location = useLocation();
    let realId = location.state.id;  /*내가쓴 글의 page에서 가져온 id*/
    let [post, modifiedPost] = useState()
    let post_contents
    let post_title;
    const navigate = useNavigate();

    useEffect(() => {
        tokenRefreshing().then(() => {
            axios.get(`/api/post/loadinfo/${realId}`,
                { withCredentials: true})
                .then((response) => {
                    modify(response.data.data);
                })
                .catch((response) => { console.log(response)});
        });
    },[]);


    return(
        <div className={styled.post_container}>
            <div className={styled.post_nav}></div>
            <div className={styled.post_main_container}>
                <div className={styled.post_main_left}>
                    <div className={styled.post_main_contents}>


                        <div className={styled.post_main_post}>
                            <div className={styled.post_post}>
                                <div className={styled.post_profile}>
                                    <div className={styled.post_profile_img}></div>
                                    <div className={styled.post_name}>
                                        <p style={{display : "block"}}>익명 </p>
                                        <input type ="text" className={styled.modify_content}
                                               onChange={(e) => {post_title = (e.target.value)}}/>

                                        <p style={{border : "solid 2px black"}}> 3분전</p>
                                    </div>
                                    <div className={styled.post_112}>

                                        <button style={{borderRight : "solid 2px #F2B284"}} onClick={()=>{
                                            axios.post(`/api/post/modify/${realId}`,
                                                {content : post_contents, title : post_title}, {
                                                    withCredentials : true,
                                                    headers : {"Content-Type": 'application/json'}
                                                }

                                            )
                                                .then((response) => {
                                                    console.log("post" + response.data.data)
                                                })
                                            navigate('/auth/MyCommentPost')
                                        }
                                        }> 변경하기</button>

                                        <button onClick={()=>{
                                            axios.delete(`/api/post/delete/${realId}`)
                                                .then((respone)=>{console.log(respone)})
                                                .catch((respone)=>{console.log(respone)})
                                            navigate('/auth/MyCommentPost')
                                        }
                                        }> 삭제 </button>
                                    </div>
                                </div>

                                <div className={styled.post_contents}>
                                    <div className={styled.post_contents_contents}>
                                        <input type ="text" className={styled.modify_content}
                                        onChange={(e) => {post_contents = (e.target.value)}}/>
                                    </div>
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

export {AfterChooseMyPost};