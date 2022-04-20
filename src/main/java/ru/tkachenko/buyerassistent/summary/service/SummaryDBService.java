package ru.tkachenko.buyerassistent.summary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistent.summary.repository.SummaryRowRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SummaryDBService {

    private final SummaryRowRepository summaryRowRepository;

    @Autowired
    public SummaryDBService(SummaryRowRepository summaryRowRepository) {
        this.summaryRowRepository = summaryRowRepository;
    }

    public void save(SummaryRowEntity summaryRowEntity) {
        summaryRowRepository.save(summaryRowEntity);
    }

    @Transactional
    public void truncateTable() {
        summaryRowRepository.truncateTable();
    }

    List<SummaryRowEntity> findAll() {
        return summaryRowRepository.findAll();
    }

    List<SummaryRowEntity> findByBranchAndAcceptMonth(String branchName, int acceptMonth) {
        return summaryRowRepository.findByBranchAndAcceptMonth(branchName, acceptMonth);
    }

    List<SummaryRowEntity> findZeroAcceptMonth() {
        return summaryRowRepository.findByAcceptMonth(0);
    }

    SummaryRowEntity findSameSpecWithNotZeroAcceptMonth(SummaryRowEntity summaryRowEntity) {
        return summaryRowRepository.findFirstBySpecAndAcceptMonthGreaterThan(summaryRowEntity.getSpec(), 0);
    }
}
