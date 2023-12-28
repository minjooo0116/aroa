package com.spring.board.member.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.spring.board.util.RedisUtil;
import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Mail 전송 클래스
 *
 * @author : minjooo
 * @fileName : EmailService
 * @since : 2023/12/26
 */

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisUtil redisUtil;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    // 이메일 내용 생성
    private MimeMessage createMessage(String code, String email) throws Exception{

        MimeMessage message = mailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("홈페이지 인증 번호입니다.");
        message.setText("안녕하세요!\n" + "\n"
                        + "본인 확인 메일입니다. 상기 번호를 입력하여 계정이 본인 소유임을 인증하여 주시기 바랍니다.\n" + "\n"
                        + "중요 : 인증번호는 3분후에 만료됩니다. 3분 내로 입력하여 주시기 바랍니다.\n" + "\n" + code);
        message.setFrom("99seed@naver.com");

        return  message;
    }

    // 이메일 보내기
    public void sendMail(String email, String code) throws Exception {

        try {

            MimeMessage mimeMessage = createMessage(code, email);
            mailSender.send(mimeMessage);
            logger.info("이메일 전송 성공", email);

        } catch (MailException mailException) {

            logger.error("이메일 전송 중 오류가 발생", mailException);
            throw new RuntimeException("이메일 전송 중 오류가 발생했습니다.");

        }
    }

    // 인증번호 생성
    public String sendCertificationMail(String email) {

        String code = createCode(6); // 코드 초기화
        logger.info("코드 초기화 완료");

        try {

            sendMail(email, code);
            redisUtil.setDataExpire(email, code, 60 * 5L); // {key,value} 형태 5분동안 저장.
            logger.info("redis에 인증번호 저장: {}, {}", email, code);

        } catch (Exception exception) {

            logger.error("redis에 인증번호 저장 중 오류 발생 {}, {}", email, code);
            code = null; // 예외 발생 시 code를 null로 설정

        }
        return code;
    }

    // 6자리 난수 생성 메소드
    private String createCode(int length) {

        StringBuilder codeBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int cerNum = (int) (Math.random() * 10); // 0부터 9까지의 난수 생성
            codeBuilder.append(cerNum);
        }

        return codeBuilder.toString();
    }
}
