import React, { useState, useEffect } from 'react';
import "../../css/layout/Layout.css";
import Header from "./Header";

function Layout(props) {
    return (
        <div className={"layout"}>
            <Header/>
            <main className={"main"}> {props.children} </main>
            {/*<Footer />       */}
        </div>
    );
}

export default Layout;