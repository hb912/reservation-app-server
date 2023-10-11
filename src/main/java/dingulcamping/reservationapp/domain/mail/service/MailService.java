package dingulcamping.reservationapp.domain.mail.service;

import dingulcamping.reservationapp.domain.mail.dto.MailDto;
import dingulcamping.reservationapp.domain.mail.exception.FailMailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    @Value("${front.server.address}")
    private String serverAddress;

    public void sendMail(MailDto mailDto, String redisKey){

        MimeMessage mimeMessage=javaMailSender.createMimeMessage();

        log.info("start send mail");
        try{
            Context context = new Context();
            context.setVariable("redisKey",redisKey);
            context.setVariable("server",serverAddress);
            String mailText = templateEngine.process("mail", context);

            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(mailDto.getTo());
            mimeMessageHelper.setSubject(mailDto.getSubject());
            mimeMessageHelper.setText(mailText, true);
            javaMailSender.send(mimeMessage);

            log.info("Success to send Mail");
        }catch(MessagingException e){
            log.error(e.getMessage());
            throw new FailMailException("메일을 전송하는데 실패하였습니다.", e);
        }
    }
}
