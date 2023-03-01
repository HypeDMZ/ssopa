import React , { useState, useEffect } from "react";
import { setCookie,getCookie,removeCookie} from "./cookie";
import axios from "axios";

function tokenRefreshing(){
    return new Promise((resolve, reject) => {
        const accessToken = getCookie('token').accessToken;
        const refreshToken = getCookie('token').refreshToken;

        const object = new Object();
        object.accessToken = accessToken;
        object.refreshToken = refreshToken;

        const data = JSON.stringify(object);

        axios.post("/api/auth/reissue",
            data,
            {
                withCredentials : true,
                headers : {"Content-Type": 'application/json'}
            }).then((res)=>{

            axios.defaults.headers.common["Authorization"] = `Bearer ${res.data.data.accessToken}`;
            console.log(axios.defaults.headers.common["Authorization"] )
            setCookie('token',
                {accessToken: `${res.data.data.accessToken}`
                    ,refreshToken: `${res.data.data.refreshToken}`},
                {
                    path: "/"
                });
            resolve(true);
        }).catch((error)=>{
            alert("재 로그인");
            window.location.href = '/auth/login';
            reject(false)
        })
    });

}

export {tokenRefreshing};