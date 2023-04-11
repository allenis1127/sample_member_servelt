package com.example.sample_member_servelt.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO {
    private Integer num;
    private String id;
    private String name;
    private String subject;
    private String content;
    private String registDay;
    private Integer hit;
    private String ip;
}
