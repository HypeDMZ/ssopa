import styled from "../css/ChattingRoom.module.css";
import {Link} from "react-router-dom";
import React from "react";

function ChattingRoom(){
    return(
        <div className={styled.post_container}>
            <div className={styled.post_nav}></div>

            <div className={styled.post_main_container}>

                <div className={styled.post_main_left}>
                    <div className={styled.post_main_contents}>
                        <div className={styled.post_friend_nav}> 친구목록 (2) </div>
                        <div className={styled.post_friend_list}>조태완바보(대전고)</div>
                    </div>
                </div>


                <div className={styled.post_main_right}>
                    <div className={styled.post_main_contents}>
                        <div className={styled.post_right_nav}>
                            <div className={styled.post_search}>
                                <div style={{width : "10%"}}>  <p style={{fontSize : "15px", color : "white"}}>채팅목록</p> </div>
                                <input style={{width : "70%", height : "80%", marginLeft : "2%",borderRadius : "15px"}} placeholder= "🔍 채팅 검색하기..."/>
                                <button style={{borderRadius : "25px", width : "10%", marginLeft : "2%" , backgroundColor : "white", color : "#F2B284", border : "none"}}> 검색 </button>
                                    <button style={{borderRadius : "25px", width : "10%", marginLeft : "2%" , backgroundColor : "white", color : "#F2B284", border : "none"}}> +생성 </button>
                            </div>
                        </div>
                        <div className={styled.post_right_list}> </div>
                    </div>
                </div>

            </div>

        </div>
    )
}

export {ChattingRoom};