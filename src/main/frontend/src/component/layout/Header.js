import React, { useState, useEffect } from 'react';
import "../../css/layout/Layout.css";
import Logo from "../../img/logo.png";

function Header(props){
    return(
        <div className={"nav"}>
            <img src={Logo} width='6%' height='6%' className={"logo"}/>
            <div>{props.component}</div>
        </div>
    )
}

export default Header;