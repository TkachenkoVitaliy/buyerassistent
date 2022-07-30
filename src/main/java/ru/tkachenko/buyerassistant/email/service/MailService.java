package ru.tkachenko.buyerassistant.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.email.entity.MailEntity;
import ru.tkachenko.buyerassistant.email.repository.MailRepository;

import java.util.List;

@Service
public class MailService {

    MailRepository mailRepository;

    @Autowired
    public MailService(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    public List<MailEntity> getAllMails() {
        return mailRepository.findAllOrderByBranchNameId();
    }

    public List<MailEntity> getMailsByBranchName(String branchName) {
        return mailRepository.findAllByBranchNameOrderById(branchName);
    }

    public void save(MailEntity mailEntity) {
        mailRepository.save(mailEntity);
    }

    public void deleteById(Long id) {
        mailRepository.deleteById(id);
    }
}
