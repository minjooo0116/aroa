package com.spring.board.member.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private int memberNo;
    private String memberPw;
    private String memberEmail;

}
