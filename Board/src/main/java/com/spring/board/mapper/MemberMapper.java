package com.spring.board.mapper;

import com.spring.board.member.model.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface MemberMapper {
    int signUp(MemberDto member);
}
