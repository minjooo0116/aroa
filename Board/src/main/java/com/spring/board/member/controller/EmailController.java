package com.spring.board.member.controller;

import com.spring.board.member.model.service.EmailService;
import com.spring.board.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Mail 전송 컨트롤러
 *
 * @author : minjooo
 * @fileName : EmailController
 * @since : 2023/12/26
 */

@RestController
@RequestMapping("")
public class EmailController {

    private final EmailService emailService;

    private final RedisUtil redisUtil;

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    public EmailController(EmailService emailService, RedisUtil redisUtil) {
        this.emailService = emailService;
        this.redisUtil = redisUtil;
    }

    // 이메일 전송 요청
    @PostMapping("/send-certification")
    public String sendCertificationEmail(@RequestBody Map<String, Object> data) {

        logger.info("이메일 전송 요청");

        String email = (String) data.get("email");
        return emailService.sendCertificationMail(email);
    }

    // 인증번호 확인
    @PostMapping("/check-certification")
    public ResponseEntity<Boolean> checkCertification(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String userCode = data.get("authKey");

        // email을 키로 사용하여 redis에 저장된 인증번호 가져오기
        String redisAuthKey = redisUtil.getData(email);

        logger.info("인증번호 확인 중: {}", userCode);
        logger.info("redisAuthKey: {}", redisAuthKey);

        boolean result = userCode.equals(redisAuthKey);

        return ResponseEntity.ok(result);
    }
}

