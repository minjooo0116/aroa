package com.spring.board.member.controller;

import com.spring.board.member.model.dto.MemberDto;
import com.spring.board.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberController {

    @Autowired
    private MemberService service;

    // 2023.12.26
    // 회원가입 페이지로 화면 전환
    @GetMapping("/signUp")
    public String signUp(){
        return "/member/signup";
    }

     // 2023.12.28
     // 회원가입 메소드
    @PostMapping("/signUp")
    public String memberSignUP(MemberDto member,
                               RedirectAttributes ra){

        System.out.println("1");
        int result = service.signUp(member);
        System.out.println(2);
        String message = result > 0 ? (member.getMemberEmail() + " 님의 가입을 환영합니다!") : "회원 가입에 실패했습니다.";

        String path = "redirect:"+(result > 0 ? "/" : "signup");

        ra.addFlashAttribute("message", message);

        return path;
    }
}
