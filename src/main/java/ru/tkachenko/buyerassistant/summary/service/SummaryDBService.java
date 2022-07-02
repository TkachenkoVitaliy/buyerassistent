package ru.tkachenko.buyerassistant.summary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistant.summary.repository.SummaryRowRepository;
import ru.tkachenko.buyerassistant.total.product.group.service.ProductGroupService;
import ru.tkachenko.buyerassistant.total.product.type.entity.ProductTypeEntity;
import ru.tkachenko.buyerassistant.total.product.type.service.ProductTypeService;
import ru.tkachenko.buyerassistant.utils.CurrentDate;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SummaryDBService {
    private final SummaryRowRepository summaryRowRepository;
    private final ProductTypeService productTypeService;
    private final ProductGroupService productGroupService;

    @Autowired
    public SummaryDBService(SummaryRowRepository summaryRowRepository, ProductTypeService productTypeService, ProductGroupService productGroupService) {
        this.summaryRowRepository = summaryRowRepository;
        this.productTypeService = productTypeService;
        this.productGroupService = productGroupService;
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

    public void updateProductTypeTable() {
        List<String> allProductTypeNames = findAllProductTypeNames();
        for(String productType : allProductTypeNames) {
            ProductTypeEntity productTypeEntity = productTypeService.findByName(productType);
            if(productTypeEntity == null) {
                productTypeEntity = new ProductTypeEntity();
                productTypeEntity.setName(productType);
                productTypeEntity.setProductGroup(productGroupService.findFirstByProductGroup("Не определена"));
                productTypeService.save(productTypeEntity);
            }
        }
    }

    public List<SummaryRowEntity> getAllUndefinedBranchRows() {
        CurrentDate currentDate = new CurrentDate();
        return summaryRowRepository.findDistinctSummaryRowEntitiesByBranchIsNull(currentDate.getYearInt());
    }
}
