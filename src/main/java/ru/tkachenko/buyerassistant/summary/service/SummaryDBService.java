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

    public List<SummaryRowEntity> findAll() {
        return summaryRowRepository.findAll();
    }

    public List<SummaryRowEntity> findByBranchAndAcceptMonthSorted (String branchName, int acceptMonth, int year) {
        return summaryRowRepository.findByBranchAndAcceptMonthAndYearOrderBySupplierProductTypeSpecPositionProfileAccept(branchName,
                acceptMonth, year);
    }

    public List<SummaryRowEntity> findZeroAcceptMonth() {
        return summaryRowRepository.findByAcceptMonth(0);
    }

    public SummaryRowEntity findSameSpecWithNotZeroAcceptMonth(SummaryRowEntity summaryRowEntity) {
        return summaryRowRepository.findFirstBySpecAndAcceptMonthGreaterThan(summaryRowEntity.getSpec(), 0);
    }

    public List<String> findUniqueSuppliersByMonthAndYear(int month, int year) {
        return summaryRowRepository.findUniqueSuppliersByMonthAndYearOrdered(month, year);
    }

    public List<String> findUniqueBranchesByMonthAndYearAndSellType(int month, int year, String sellType) {
        return summaryRowRepository.findUniqueBranchesByMonthAndYearAndSellTypeOrdered(month, year, sellType);
    }

    public List<SummaryRowEntity> findAllRowsByMonthAndYear(int month, int year) {
        return summaryRowRepository.findAllByAcceptMonthAndYear(month, year);
    }

    public List<String> findAllProductTypeNames() {
        return summaryRowRepository.findAllProductTypeNames();
    }
}
