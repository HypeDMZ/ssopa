/*
<수정사항>
1.하트 버튼 누르면 한없이 추가된다. 스위치 변수 하나 놔서 한번 누르면 올라가고 한번 더 누르면 취소되게 구현해야한다.
2. 각 게시판의 table들을 아직 구현하지 않음.
3. TabContent의 html코드들이 너무 복잡해서 나중에 다 외부 file로 뺴서 사용해야 좋을듯하다.(V)
4.글쓰기, 수정하기, 내용 눌렀을떄 나타나는 창들? 을 구현해야된다.
5. 동아리, 시험, 일정 게시판 table 변경해야한다.
____________________________________________________________________________________________________________________________
<프로토콜>
1.모든 게시판들은 TabContent 컴포넌트에서 구현된다.
2.post값에 무엇이 들어가는지에 따라서 게시판띄우는 게 달라진다 -> menu항목추가시 Nav-item을 늘리거나 수정하면됨.
3.로그아웃 버튼을 누르면 localhost:3000/로 이동한다.
4. 급식, 학사일정은 글쓰기가 금지되게 한다 -> 학생들이 마음대로 바꿀상황 대비.
 */

import "../css/Post.css";
import {Nav, Table} from 'react-bootstrap';
import React, {useState} from "react";
import Logo from "../img/logo.png";
import {Navigate, useNavigate} from "react-router-dom";
import {Post_School} from "./Post_Menu/Post_School";
import {Post_Lunch} from "./Post_Menu/Post_Lunch";
import {Post_Active} from "./Post_Menu/Post_Active";
import {Post_Exam} from "./Post_Menu/Post_Exam";
import {Post_Plan} from "./Post_Menu/Post_Plan";

function Post()
{
    let[post,changePost] = useState("school");

    let[lunchContents, changeLunch] =useState([{date : "11/23", cnt :"🛒", count : '1'},
        {date : "11/24", cnt :"🛒", count : '3'},
        {date : "11/25", cnt :"🛒", count : '5'}])

    let[school,changecontents] = useState([{name : "kim", cnt :"시험범위 아는분", count : 1, good : 2},
        {name : "park", cnt :"놀사람", count : 1, good : 11},{name : "lee", cnt :"공부할사람", count : 1, good : 22}])

    let nav = useNavigate();
    return(
        <div className="post-Container">
            <div className="post-nav">

                <img src={Logo} width='6%' height='6%' className={"logo"} style ={{display : "inline", float : "left"}}/>

                <Nav variant="tabs"  defaultActiveKey="school" style={{padding : "10px", marginLeft :"20px", textDecoration : "none"}} className="post-buttons">
                    <Nav.Item className ="post-Menu">
                        <Nav.Link eventKey="school" style={{padding : "10px", marginLeft :"20px", textDecoration : "none"}}
                                  onClick={()=> {changePost("school")}}> <div>학교 게시판</div> </Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                        <Nav.Link eventKey="lunch" style={{padding : "10px", marginLeft :"20px" , textDecoration : "none"}}
                                  onClick={()=> {changePost("lunch")}}><div>급식 정보</div></Nav.Link>
                    </Nav.Item>

                    <Nav.Item>
                        <Nav.Link eventKey="active" style={{padding : "10px", marginLeft :"20px" , textDecoration : "none"}}
                                  onClick={()=> {changePost("active")}}> <div>동아리 게시판</div> </Nav.Link>
                    </Nav.Item>

                    <Nav.Item>
                        <Nav.Link eventKey="active" style={{padding : "10px", marginLeft :"20px" , textDecoration : "none"}}
                                  onClick={()=> {changePost("exam")}}> <div>시험 게시판</div> </Nav.Link>
                    </Nav.Item>

                    <Nav.Item>
                        <Nav.Link eventKey="plan" style={{padding : "10px", marginLeft :"20px" , textDecoration : "none"}}
                                  onClick={()=> {changePost("plan")}}> <div>학교 일정</div> </Nav.Link>
                    </Nav.Item>
                </Nav>
                <button className="post-logout" style={{fontSize : "15px", width : "100px", color : "black", borderRadius : "15px"}}
                onClick={()=>{
                    nav('/');
                }
                }>로그아웃</button>
            </div>

            <div className ="post-contents">
                <TabContent post ={post} lunchContents={lunchContents} school ={school} changecontents={changecontents}/>
            </div>
        </div>
    )
}

function TabContent({post, lunchContents, school, changecontents}) {
    if (post === "school") {
       return(<Post_School school ={school} changecontents={changecontents}/>)
    }

    if (post === "lunch") {
        return <Post_Lunch lunchContents={lunchContents}/>
    }
    if (post === "active") {
      return(<Post_Active/>)
    }
    if (post === "exam") {
       return (<Post_Exam/>)
    }
    if (post === "plan") {
      return (<Post_Plan/>)
    }
}


export  {Post};