import styled from "../css/ChoosePost.module.css";
import {Link} from "react-router-dom";
import React from "react";

function ChoosePost(){
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
                                        <p>대전고등학교 </p>
                                        <p style={{border : "solid 2px black"}}> 3분전</p>
                                    </div>
                                    <div className={styled.post_112}>
                                        <button style={{borderRight : "solid 2px #F2B284"}}>신고</button>
                                        <button>채팅</button>
                                        <button>📋</button>
                                    </div>
                                </div>
                                
                                
                                <div className={styled.post_contents}>
                                    <div className={styled.post_contents_contents}><button>아 졸리다...ㅜ,.ㅜ</button></div>
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