package com.example.sample_member_servelt.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RippleDTO {
    private Integer rippleId;
    private Integer boardNum;
    private String memberId;
    private String name;
    private String content;
    private String insertDate;
    private String ip;
    private boolean isLogin; // 댓글 작성자가 로그인인지
}
