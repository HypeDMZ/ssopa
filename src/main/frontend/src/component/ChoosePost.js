import styled from "../css/ChoosePost.module.css";
import {useLocation} from "react-router-dom";
import React, {useState, useEffect} from "react";
import axios from 'axios'

function ChoosePost(){
    let location = useLocation();
    let id = location.state.id;
    const currentTime = new Date().toLocaleTimeString();

    const [title,setTitle] = useState('');
    const [content, setContent] = useState('');
    const [writer, setWriter] = useState('');
    const [time, setTime] = useState('');

    useEffect(()=>{
        axios.get(`/api/post/loadinfo/${id}`,{
            withCredentials: true
        }).then(res=>{
            console.log(res.data.data);

            setTitle(res.data.data.title);
            setContent(res.data.data.content);
            setWriter(res.data.data.writer);
            setTime(res.data.data.created_date.slice(0,10));

        })
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
                                        <p style={{display : "block"}}>{writer}</p>
                                        <p>{title} </p>
                                        <p style={{border : "solid 2px black"}}>{time}</p>
                                    </div>
                                    <div className={styled.post_112}>
                                        <button style={{borderRight : "solid 2px #F2B284"}}>신고</button>
                                        <button>채팅</button>
                                        <button>📋</button>
                                    </div>
                                </div>
                                
                                
                                <div className={styled.post_contents}>
                                    <div className={styled.post_contents_contents}><button>{content}</button></div>
                                    <div className={styled.post_good}>
                                        <button>👍 0 </button>
                                        <button>📖 0 </button>
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

export {ChoosePost};