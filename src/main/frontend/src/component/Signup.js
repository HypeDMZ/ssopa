import React, { useState, useEffect } from 'react';
import '../css/Singup.css';
import axios from "axios";

function Signup(props){

    const [Email, setEmail] = useState("");
    const [Name, setName] = useState("");
    const [Password, setPassword] = useState("");
    const [ConfirmPassword, setConfirmPassword] = useState("");

    const onEmailHandler = (event) => {
        setEmail(event.currentTarget.value);
    }
    const onNameHandler = (event) => {
        setName(event.currentTarget.value);
    }
    const onPasswordHandler = (event) => {
        setPassword(event.currentTarget.value);
    }
    const onConfirmPasswordHandler = (event) => {
        setConfirmPassword(event.currentTarget.value);
    }
    const onSubmitHandler = (event) => {
        event.preventDefault();

        if(Password !== ConfirmPassword){
            return alert('비밀번호와 비밀번호 확인이 같지 않습니다.')
        }

        let body = {
            email: Email,
            name: Name,
            password: Password,
            confirmPassword: ConfirmPassword,
        }

        axios.post('https://dmz02.com', body)
        .then(response => {
            if(response.payload.success){
                console.log(response)
                //token.innerHTML = response.data.token;
            }
            else alert('error')
        });
    
        /*dispatch(registerUser(body))
        .then(response => {
            if(response.payload.success){
                props.history.push('/Login')
            } else {
                alert('Error')
            }
        })*/
    }


    return (
        <div>
            <Nav></Nav>

            <div className='join' style={{ 
            display: 'flex', justifyContent: 'center', alignItems: 'center', 
            width: '100%', height: '100vh'
            }}>
            <form style={{ display: 'flex', flexDirection: 'column'}}
                onSubmit={onSubmitHandler}
            >
                <h4>Email</h4>
                <input className='inputBox' type='email' value={Email} placeholder = '입력해주세요' onChange={onEmailHandler}/>
                <h4>Name</h4>
                <input className='inputBox' type='text' value={Name} placeholder = '입력해주세요' onChange={onNameHandler}/>
                <h4>Password</h4>
                <input className='inputBox' type='password' value={Password} placeholder = '입력해주세요' onChange={onPasswordHandler}/>
                <h4>Confirm Password</h4>
                <input className='inputBox' type='password' value={ConfirmPassword} placeholder = '입력해주세요' onChange={onConfirmPasswordHandler}/>
                <br />
                <button className='joinB' formAction=''>
                    회원가입
                </button>
            </form>
        </div>
        </div>
    )
}

function Nav(){
    return(
        <div className="nav">
            <h2>DMZ - Join</h2>
        </div>
    )
  }

export {Signup};