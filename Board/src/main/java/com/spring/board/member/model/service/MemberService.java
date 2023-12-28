package com.spring.board.member.model.service;

import com.spring.board.mapper.MemberMapper;
import com.spring.board.member.model.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberMapper memberMapper;

    @Autowired
    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Transactional
    public int signUp(MemberDto member) {
        return memberMapper.signUp(member);
    }
}
