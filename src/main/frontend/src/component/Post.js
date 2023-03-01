import React, {useEffect, useState} from "react";
import Layout from '../css/layout/Layout.css'
import styled from '../css/Post.module.css'
import {Link} from 'react-router-dom';
import axios from 'axios'
import Form from 'react-bootstrap/Form';
import {tokenRefreshing} from "../function/tokenRefreshing";
import {getCookie} from "../function/cookie";
import InfiniteScroll from "react-infinite-scroll-component";
import {useNavigate} from "react-router-dom";
import { BsPerson, BsFillPencilFill,BsFillBookmarkCheckFill, BsFillChatTextFill} from "react-icons/bs";


//변경사항
/*
 1. icon라이브러리 설치 : npm install react-icons --save
*/
function Post()
{
    const navigate = useNavigate();
    let [게시판, 게시판Change] = useState([])
    let room = "test";
    let [page,pageChange] = useState(0);

    /*재 랜더링 할때마다 server에서 최신 post를 가져온다.*/
    useEffect(() => {
        tokenRefreshing().then(() => {
            console.log(page)
            axios.get(`/api/post/load/${room}/${page}`,
                { withCredentials: true})
                .then((response) => {
                    console.log(response.data.data);
                    게시판Change(게시판 => [...게시판,...response.data.data]);
                })
                .catch((response) => { console.log(response) });
        });
    }, [page]);


    const handleScroll = () => {
        pageChange((page) => page + 1);
    }

    return(
        <div className={styled.post_container}>
            <div className={styled.post_my}>
                <div style={{margin : "10px", borderBottom : "solid 1.5px #F2B284", padding : "2px", color : "#F2B284" ,display : "inline-block"}}>profile</div>
                <button style={{float : "right", fontSize : "20px", backgroundColor : "wheat", border : "none"}}> ⚙ </button>
                <div className={styled.post_profile}>
                    <div className={styled.post_profile_img}></div>
                    <div className={styled.post_profile_info}>
                        <p style={{textAlign : "center"}}>성이름(대전고)</p>
                        <button style={{width: "80%", height : "30px", borderRadius : "10px", backgroundColor : "#FBD0B2"}}>내 정보</button>
                    </div>
                    <div className={styled.post_profile_catagory}>
                        <div><Link to = "/auth/MyCommentPost" style={{textDecoration : "none"}}>✍️ 내가 쓴 글</Link></div>
                        <div><Link to = "#" style={{textDecoration : "none"}}>📖 댓글 단 글</Link></div>
                        <div><Link to = "/auth/AfterClickBookMark" style={{textDecoration : "none"}}><BsFillBookmarkCheckFill style={{color : "#F2B284"}}/> 책갈피</Link></div>
                        <div><Link to ="/auth/chattingRoom" style={{textDecoration : "none"}}><BsFillChatTextFill style={{color : "#F2B284"}}/> 오픈채팅방</Link></div>
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
                        <input className={styled.post_input} style={{width : "70%", height : "70%", borderRadius : "15px"  , border : "solid 2px #F2B284", marginLeft : "10px"}} placeholder= "전체 게시판의 글을 입력해보세요"/>
                        <button onClick={()=>{ }} style={{float : "right", marginTop : "5px"}}>검색</button>
                    </div>
                    <div id="scrollableDiv" className={styled.post_main_contents}>
                        <div className={styled.post_main_nav}>
                            <Form.Select size="sm" onChange={(e)=>{console.log(e.target.value)}} style={{width : "20%", height : "60%"}}>
                                <option>자유게시판</option>
                                <option>학급 게시판</option>
                                <option>시험 게시판</option>
                                <option>정보게시판</option>
                            </Form.Select>

                            <button style={{float : "right", borderRadius : "10px", backgroundColor : "#F2B284", width : "20%", height : "80%"}}
                            onClick={()=>{
                                navigate("/post/add");
                            }}> <BsFillPencilFill style={{color : "white"}}/>글쓰기 </button>
                        </div>

                        <div className={styled.post_main_post}>
                            <InfiniteScroll
                                dataLength={게시판.length}
                                next={handleScroll}
                                hasMore={true}
                                loader={<h4>로딩이다</h4>}
                                scrollableTarget="scrollableDiv"
                                >
                                {
                                    /*[0,1,2...]말고 post를 받아와서 사용하면됨.*/
                                    게시판.map( (a,i)=>{
                                       let  date = a.modified_date[0] +"-"+ a.modified_date[1] + "-" + a.modified_date[2]

                                        return(
                                            <div className={styled.post_post} key={a.id} onClick={()=>{navigate("/auth/choosePost",{state: {'title' : a.title, 'id' : a.id} })}}>
                                                <div className={styled.post_title}>
                                                    <div className={styled.post_img}><BsPerson style={{position : "absolute", marginLeft : "7px", marginTop : "6px"}}></BsPerson></div>
                                                    <span className={styled.post_name}>익명</span>
                                                    <span className={styled.post_time} style= {{fontSize : "10px"}}> {date} </span>
                                                    <p className={styled.post_channel}>[자유게시판]</p>
                                                </div>

                                                <div className={styled.post_content}>
                                                    {a.title}
                                                </div>
                                            </div>
                                        )
                                    })
                                }
                            </InfiniteScroll>
                        </div>
                    </div>
                </div>
                <div className={styled.post_main_right}>
                    <div className={styled.post_main_hotTopic}>
                        <div className={styled.post_hotTopic_nav}>Hot Topic</div>
                        <div className={styled.post_hotTopic_main}>
                            <div> 1번 주제</div>
                            <div> 1번 주제 </div>
                            <div>1번 주제 </div>
                            <div>1번 주제 </div>
                            <div>1번 주제 </div>
                        </div>

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