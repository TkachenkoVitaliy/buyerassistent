package ru.tkachenko.buyerassistant.email.service;

import org.springframework.stereotype.Repository;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;

@Repository
public interface EmailSenderService {

    public void sendMailWithAttachment (String toAddress, String subject, String message, String attachmentPath)
            throws MessagingException, FileNotFoundException;
}
