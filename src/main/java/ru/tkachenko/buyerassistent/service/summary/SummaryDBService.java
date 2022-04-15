package ru.tkachenko.buyerassistent.service.summary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistent.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistent.repository.SummaryRowRepository;

import javax.transaction.Transactional;

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
}
