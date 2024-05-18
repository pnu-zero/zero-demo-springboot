package com.example.zero.mail.service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.net.InetAddress;
import java.net.UnknownHostException;


@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine htmlTemplateEngine;

    @Value("${server.port}")
    private String port;

    private final String protocol = "http://";

    private String generateActivationLink(String verificationCode, Long groupId, Long userId) throws UnknownHostException {
        String hostAddr = InetAddress.getLocalHost().getHostAddress();
        return protocol + hostAddr + ":" + port + "/api/user/activate?auth_code=" + verificationCode + "&user_id=" + userId + "&group_id=" + groupId;
    }

    public String sendAuthMail(String clientEmail, String verificationCode, Long groupId, Long userId) throws Exception {
        Context context = new Context();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

        InternetAddress from = new InternetAddress("juniper0917@gmail.com", "PNU Cloud");
        InternetAddress to = new InternetAddress(clientEmail);

        String activationLink = generateActivationLink(verificationCode, groupId, userId);

        context.setVariable("text", activationLink);

        String htmlTemplate = htmlTemplateEngine.process("mail/authMail", context);
        messageHelper.setFrom(from);
        messageHelper.setTo(to);
        messageHelper.setSubject("[PNU Cloud] 사용자 인증 요청 메일");
        messageHelper.setText(htmlTemplate, true);
        javaMailSender.send(mimeMessage);

        return null;
    }
}
