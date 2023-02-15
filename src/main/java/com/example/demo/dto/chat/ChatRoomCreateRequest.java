package com.example.demo.dto.chat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomCreateRequest {
    @ApiModelProperty(value="채팅방 제목", example="태완이가 바보임을 증명하는 채팅방",required = true)
    private String title;


}