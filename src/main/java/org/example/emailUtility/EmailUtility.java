package org.example.emailUtility;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailUtility {
    public void sendEmail(String email, String subject, String body) {
        JavaMailSenderImpl gmailSender = createGmailSenderWithIdAndPassword();
        try {
            MimeMessage message = gmailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("tashildar563@gmail.com");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body);
            gmailSender.send(message);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

        private static JavaMailSenderImpl createGmailSenderWithIdAndPassword() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        mailSender.setUsername("<username>");
        mailSender.setPassword("<password>");
        return mailSender;
    }
}
