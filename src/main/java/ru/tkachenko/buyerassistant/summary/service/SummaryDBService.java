package ru.tkachenko.buyerassistant.summary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistant.summary.repository.SummaryRowRepository;

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


    List<SummaryRowEntity> findByBranchAndAcceptMonthSorted (String branchName, int acceptMonth) {
        return summaryRowRepository.findByBranchAndAcceptMonthOrderBySupplierProductTypeSpecPositionProfileAccept(branchName,
                acceptMonth);
    }

    List<SummaryRowEntity> findZeroAcceptMonth() {
        return summaryRowRepository.findByAcceptMonth(0);
    }

    SummaryRowEntity findSameSpecWithNotZeroAcceptMonth(SummaryRowEntity summaryRowEntity) {
        return summaryRowRepository.findFirstBySpecAndAcceptMonthGreaterThan(summaryRowEntity.getSpec(), 0);
    }
}
