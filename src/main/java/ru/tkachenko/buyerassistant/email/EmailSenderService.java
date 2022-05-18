package ru.tkachenko.buyerassistant.email;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;

public interface EmailSenderService {

    public void sendMailWithAttachment (String toAddress, String subject, String message, String attachmentPath)
            throws MessagingException, FileNotFoundException;
}
