package ru.tkachenko.buyerassistant.email.service;

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
public class DefaultEmailSenderService implements EmailSenderService {

    private JavaMailSender javaMailSender;

    @Autowired
    public DefaultEmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMailWithAttachment(String toAddress, String subject, String message, String attachmentPath) throws MessagingException, FileNotFoundException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(attachmentPath));
        messageHelper.setFrom("zakup@vtkachenko.ru");
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);
        messageHelper.setText(message);
        messageHelper.addAttachment(file.getFilename(), file);
        javaMailSender.send(mimeMessage);
    }
}
