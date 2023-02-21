import {Link, useNavigate, useParams} from "react-router-dom";
import React, {useState} from "react";
import styled from "../css/FindId.module.css"
import Layout from "./layout/Layout";

function ShowId(props){
    const {phoneNumber} = useParams();
    let [id] = useState();
    const navigate = useNavigate();

    return(
        <Layout>
            <div style={{width: "100%", height: "100%"}}>
                {/*<Nav></Nav>*/}

                <h2 style={{borderBottom : '2px solid black',margin : '30px'}}>아이디 찾기</h2>
                <div className={styled.find} style={{
                    display: 'flex', justifyContent: 'center', alignItems: 'center',
                    width: '100%', height: '100vh'
                }}>
                    <div className={styled.findId} style={{height: "30%" ,display: 'flex', flexDirection: 'column',  padding: "10%"}}>
                        <h3>고객님의 정보와 일치하는 아이디입니다</h3>
                        <br/><br/>
                        <h3 style={{borderBottom: "2px solid black"}}>{phoneNumber}</h3>
                    </div>

                    <button onClick={()=>{
                        navigate('/auth/login');
                    }}>
                        로그인하기
                    </button>
                </div>
            </div>
        </Layout>
    )
}
function Nav(){
    return(
        <div className={styled.nav}>
            <Link to="/">
                <h2 style={{color: "white"}}>DMZ</h2>
            </Link>
        </div>
    )
}
export {ShowId}