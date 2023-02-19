import React from "react";
import Styled from "../../css/Post.module.css"
function Post_School({school, changecontents})
{
    return (<div className={Styled.post_post}>
        <div className={Styled.post_custom}>
            <div className={Styled.post_custom1}> <a href ="#">게시판1</a> </div>

            <div className={Styled.post_custom2}> <a href ="#">게시판2</a> </div>

            <div className={Styled.post_custom3}> <a href ="#">게시판3</a> </div>

            <div className={Styled.post_custom4}> <a href ="#">게시판4</a> </div>
        </div>
        <button className={Styled.post_update}>글쓰기</button>
        <table style={{border: "2px solid", width: "100%", scrollbarWidth : "5px"}}>
            <tr>
                <td>#</td>
                <td>제목</td>
                <td>내용</td>
                <td>좋아요</td>
                <td>글수정</td>
                <td>조회수</td>
            </tr>

            <tbody>
            {
                school.map(function (a, i) {
                    return (
                        <tr>
                            <td style={{width: "10%"}}>{i}</td>
                            <td style={{width: "10%"}}> {school[i].name} </td>
                            <td style={{width: "50%"}}><a href=".#">{school[i].cnt}</a></td>
                            <td style={{width: "5%"}}>
                                <button onClick={() => {
                                    let copy = [...school];
                                    copy[i].good = copy[i].good + 1;
                                    changecontents(copy);
                                }}>❤️
                                </button>
                                {school[i].good} </td>
                            <td style={{width: "5%"}}>
                                <button>수정</button>
                            </td>
                            <td style={{width: "5%", textAlign: "center"}}> {school[i].count} </td>
                        </tr>
                    )
                })
            }
            </tbody>

        </table>
    </div>)
}

export {Post_School};