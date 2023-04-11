package com.example.sample_member_servelt.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
    private String id;
    private String password;
    private String name;
    private String gender;
    private String birth;
    private String mail;
    private String phone;
    private String address;
}
