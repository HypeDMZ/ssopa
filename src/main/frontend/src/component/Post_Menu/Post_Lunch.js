import React from "react";

function Post_Lunch({lunchContents}){
    return(
        <div>
            <table style={{border: "solid", width: "100%"}}>
                <tr>
                    <td>날짜</td>
                    <td>급식보기</td>
                    <td>조회수</td>
                </tr>

                <tbody>
                {
                    lunchContents.map(function (a, i) {
                        return (
                            <tr>
                                <td> {lunchContents[i].date} </td>
                                <td>
                                    <button>{lunchContents[i].cnt}</button>
                                </td>
                                <td> {lunchContents[i].count} </td>
                            </tr>
                        )
                    })
                }
                </tbody>

            </table>
        </div>
    )
}

export {Post_Lunch}