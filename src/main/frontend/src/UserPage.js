//UserPage.js (Redux module)
import axios from "axios";
//API통신을 통해 서버에 id,pwd를 제공하고 유저 정보와 토큰을 받아 저장
const loginDB = (id, password) => {
    return function (dispatch, getState, { history }) {
        axios({
            method: "post",
            url: "/auth/login",
            data: {
                email: id,
                password: password,
            },
        })
            .then((res) => {
                console.log(res);
                dispatch(
                    setUser({
                        email: res.data.email,
                        nickname: res.data.nickname,
                    })
                );
                const accessToken = res.data.token;
                //쿠키에 토큰 저장
                setCookie("is_login", `${accessToken}`);
                document.location.href = "/";
            })
            .catch((error) => {
                console.log(error);
            });
    };
};

//회원가입 API
const signUpDB = (id, password, nickname) => {
    return function (dispatch, getState, { history }) {
        axios({
            method: "post",
            url: "auth/signup",
            data: {
                email: id,
                password: password,
                nickname: nickname,
            },
        })
            .then((res) => {
                window.alert(res.data.result);
            })
            .catch((error) => {
                console.log(error);
            });
    };
};