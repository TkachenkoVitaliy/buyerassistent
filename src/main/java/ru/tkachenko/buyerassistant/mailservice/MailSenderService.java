package ru.tkachenko.buyerassistant.mailservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;

@Service
public class MailSenderService {

    private JavaMailSender javaMailSender;

    @Autowired
    public MailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendMailWithAttachment(String toAddress, String subject, String message, String attachment)
            throws MessagingException, FileNotFoundException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom("VTKACHENKO.RU");
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);
        messageHelper.setText("Это автоматическая рассылка, пожалуйста не отвечайте на это письмо");
        FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(attachment));
        messageHelper.addAttachment("Акцепт/отгрузка", file);
        javaMailSender.send(mimeMessage);
    }
}
