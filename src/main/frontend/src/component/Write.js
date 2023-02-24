import React, {useEffect, useState} from "react";
import Layout from '../css/layout/Layout.css'
import styled from '../css/Write.module.css'
import Form from 'react-bootstrap/Form';
import imgUploadButton from '../img/imgUploadBtn.png';
import axios from "axios";
import {useParams} from "react-router-dom";

function Write()
{
    const [게시판, 게시판설정] = useState("");
    const [제목, 제목설정] = useState("");
    const [내용, 내용설정] = useState("");
    let [postDataJSON, postDataJSONChange] = useState([]);
    let postData = new Object();

    useEffect( () => {
        postData.category = "뜨밤";
        postData.content = 내용;
        postData.title = 제목;

        postDataJSONChange(JSON.stringify(postData));
    },[내용, 제목]);
    const selectHandler = (e) => {
        게시판설정(e.target.value);
    }
    const onTitleHandler = (e) => {
        제목설정(e.target.value);
    }
    const onContentHandler = (e) => {
        내용설정(e.target.value);
    }

    const onSendHandler = (e) => {
        console.log(게시판 + 내용 + 제목);
        console.log(axios.defaults.headers.common["Authorization"]);
        axios.post("http://localhost:8080/post/add", postDataJSON, {
            withCredentials : true,
            headers : {"Content-Type": 'application/json'}
            })
            .then((result)=> {
                alert('게시물 등록 성공!');
                console.log(result.data);
                //<Link to={'/auth/showid/${Phone}'}></Link>
                //navigate('/auth/showid/'+result.data.data.email);
            })
            .catch((response)=>{console.log('이상하다')})
    }
    const options = [
        {value : "1", name: "one"},
        {value : "2", name: "two"},
        {value : "3", name: "three"},
    ];

    return(
        <div className={styled.post_container}>
            <div className={styled.post_nav}></div>
            <div className={styled.post_main_container}>
                <div className={styled.post_main_left}>
                    <div className={styled.post_main_contents}>
                        <div className={styled.post_main_nav}>
                            <Form.Select aria-label="Default select example" className={styled.post_select} onChange={selectHandler}>
                                <option>--게시판을 선택하세요--</option>
                                {
                                    options.map((option) => (
                                        <option key={option.value} value={option.value}>
                                            {option.name}
                                        </option>
                                    ))
                                }
                            </Form.Select>
                        </div>
                        <div className={styled.post_main_post}>
                            <input type={"text"} placeholder={"제목을 입력하세요"}
                                style={{fontSize: "20px",height: "50px", width: "100%", border: "none", borderBottom: "solid 2.5px #F2B284"}}
                                onChange={onTitleHandler}></input>
                            <br/>
                            <br/>
                            <textarea placeholder={"내용을 입력하세요"} style={{height: "80%", width: "100%", border: "none",
                                resize: "none", fontSize: "20px"}}
                                onChange={onContentHandler}></textarea>
                            <div style={{borderTop: "solid 2.5px #F2B284",width: "100%", height: "10%"}}>
                                <img src={imgUploadButton} style={{width: "50px", height: "50px", marginTop: "2%"}}/>
                                <button onClick={onSendHandler} style={{ width: "100px", height: "40px" , border: "none",
                                    borderRadius: "15px", color: "white", backgroundColor: "#E87D30",
                                    position: "relative", left: "80%", bottom: "30%", fontSize: "20px"}}>등록</button>
                            </div>
                        </div>

                    </div>
                </div>
                <div className={styled.post_main_right}>
                    <div className={styled.post_main_hotTopic}>
                        <div className={styled.post_hotTopic_nav}>Hot Topic</div>
                    </div>
                    {/*<div className={styled.post_main_lunch}>
                        <div className={styled.post_Lunch_nav}>급식 게시판</div>
                    </div>*/}

                </div>
            </div>
        </div>
    )
}

export {Write};