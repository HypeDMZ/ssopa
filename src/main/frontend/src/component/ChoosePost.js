import styled from "../css/ChoosePost.module.css";
import {useLocation} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {tokenRefreshing} from "../function/tokenRefreshing";
import axios from "axios";
import {BsPerson} from "react-icons/bs";

function ChoosePost(){
    let location = useLocation();
    let title = location.state.title;
    let id = location.state.id;
    console.log(id)

    let cmt
    let [comments, modify] = useState([])

    useEffect(() => {
        tokenRefreshing().then(() => {
            axios.get(`/api/comment/list/${id}`,
                { withCredentials: true})
                .then((response) => {
                    modify(response.data.data);
                    console.log(response.data.data)
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
                                        <p style={{display : "block"}}>익명</p>
                                        <p>{title} </p>
                                        <p style={{border : "solid 2px black"}}> 3분전</p>
                                    </div>
                                    <div className={styled.post_112}>
                                        <button style={{borderRight : "solid 2px #F2B284"}}>신고</button>
                                        <button>채팅</button>
                                        <button>📋</button>
                                    </div>
                                </div>
                                
                                
                                <div className={styled.post_contents}>
                                    <div className={styled.post_contents_contents}><button> Contents만들어주세요</button></div>
                                    <div className={styled.post_good}>
                                        <button>👍 0 </button>
                                        <button>📖 0 </button>
                                    </div>
                                </div>
                            </div>
                            <div className={styled.comments_background}>
                                    {
                                        comments.map((a,i)=>{
                                            return(
                                                <div className={styled.comments}>
                                                    <div className={styled.post_img}><BsPerson style={{position : "absolute", marginLeft : "7px", marginTop : "6px"}}></BsPerson></div>
                                                            <span style={{display : "inline"}}> 익명 </span>
                                                            <p> {a.comment} </p>
                                                </div>
                                            )
                                        })
                                    }
                            </div>

                            <div className={styled.commnetsWrite}>
                                <input style={{width :"80%", height : "97%", fontSize : "20px"}} onChange={(e)=>{
                                    cmt = e.target.value
                                    console.log(e.target.value)
                                }
                                }/>
                                <button style={{width: " 10%", height : "80%", marginLeft : "2%", borderRadius : "15px"}} onClick={()=>{
                                    axios.post(`/api/comment/write`,{comment : cmt, id : id}
                                        , {
                                            withCredentials : true,
                                            headers : {"Content-Type": 'application/json'}
                                        })
                                        .then(response =>{
                                            console.log(response)
                                            axios.get(`/api/comment/list/${id}`,
                                                { withCredentials: true})
                                                .then((response) => {
                                                    modify(response.data.data);
                                                    console.log(response.data.data)
                                                })
                                                .catch((response) => { console.log(response)});
                                        })
                                    console.log(cmt)
                                }
                                }> 전송</button>
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

export {ChoosePost};