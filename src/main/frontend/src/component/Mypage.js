import React, {useEffect, useState} from "react";
import Layout from '../css/layout/Layout.css'
import styled from '../css/Write.module.css'
import axios from "axios";
import {tokenRefreshing} from "../function/tokenRefreshing";
import {removeCookie} from "../function/cookie";
import { loadTossPayments } from '@tosspayments/payment-sdk'
import {useNavigate} from "react-router-dom";
const clientKey = 'test_ck_OALnQvDd2VJY4EYbwMx3Mj7X41mN'
function Mypage()
{
    const [게시판, 게시판설정] = useState("");

    let [postDataJSON, postDataJSONChange] = useState([]);
    let merchandise_data = new Object();



    useEffect(()=>{

        merchandise_data.amount=1000;
        merchandise_data.name="닉네임 변경";

        tokenRefreshing()
        axios.post("/api/payment/request", merchandise_data, {
            withCredentials : true,
            headers : {"Content-Type": 'application/json'}
        })
            .then((result)=> {
                alert('결제 객체 생성 완료');
                console.log(result.data);
                loadTossPayments(clientKey).then(tossPayments => {
                    tossPayments.requestPayment('카드', { // 결제 수단 파라미터
                        // 결제 정보 파라미터
                        amount: merchandise_data.amount,
                        orderId: result.data.orderId,
                        orderName: result.data.name,
                        customerName: '박토스',
                        successUrl: 'https://localhost:3000/api/payment/success',
                        failUrl: 'https://localhost:3000/api/payment/fail',
                    })
                        .catch(function (error) {
                            if (error.code === 'USER_CANCEL') {
                                // 결제 고객이 결제창을 닫았을 때 에러 처리
                            } else if (error.code === 'INVALID_CARD_COMPANY') {
                                // 유효하지 않은 카드 코드에 대한 에러 처리
                            }
                        })
                })
                //<Link to={'/auth/showid/${Phone}'}></Link>
                //navigate('/auth/showid/'+result.data.data.email);
            })
            .catch((response)=>{console.log('이상하다')})

    },[])




    return(
        <div className={styled.post_container}>
            <div className={styled.post_nav}></div>
            <div className={styled.post_main_container}>
                <div className={styled.post_main_left}>
                    <div className={styled.post_main_contents}>
                        <div className={styled.post_main_nav}>

                        </div>
                        <div className={styled.post_main_post}>



                            <div style={{borderTop: "solid 2.5px #F2B284",width: "100%", height: "10%"}}>


                            </div>
                        </div>

                    </div>
                </div>

            </div>
        </div>
    )
}

export {Mypage};