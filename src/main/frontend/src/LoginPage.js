//LoginPage.js

/*생략*/
const [id, setId] = React.useState("");
const [pwd, setPwd] = React.useState("");

/*생략*/

const LoginPage = () => {
//입력 값 정합성 체크 후 login API 요청
    if (id === "" || pwd === "") {
        window.alert("아이디와 비밀번호를 입력해주세요.");
        return;
    }
    if (!emailCheck(id)) {
        window.alert("이메일 형식이 맞지 않습니다.");
    }
    dispatch(userActions.loginDB(id, pwd));
};

/*생략*/

<Input
    _onChange={(e) => {
        setId(e.target.value);
    }}
    width="380px"
    height="45px"
    placeholder="아이디"
/>
<Input
    _onChange={(e) => {
        setPwd(e.target.value);
    }}
    width="380px"
    height="45px"
    placeholder="비밀번호"
    type="password"
/>