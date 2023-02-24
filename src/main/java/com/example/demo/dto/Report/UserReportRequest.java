package com.example.demo.dto.Report;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiOperation(value = "유저 신고 처리 요청")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReportRequest {
    //이 Dto 안에는 reportedUserId 와 신고내용인 content를 받는다.
    @ApiModelProperty(value = "신고 할 사용자 ", notes ="신고할 사람의 아이디를 입력하시오.", required = true, example = "3")
    @NotNull(message = "신고할 유저의 아이디 입력해주세요.")
    private Long reportedUserId;

    @ApiModelProperty(value = "신고 사유", notes = "신고 사유를 입력해주세요.", required = true, example = "Insulting")
    @NotBlank(message = "신고 사유를 입력하세요.")
    private String content;

}

