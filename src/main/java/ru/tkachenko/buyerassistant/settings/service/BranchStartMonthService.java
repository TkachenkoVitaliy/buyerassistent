package ru.tkachenko.buyerassistant.settings.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.settings.entity.BranchStartMonthEntity;
import ru.tkachenko.buyerassistant.settings.repository.BranchStartMonthRepository;

import java.util.List;

@Service
public class BranchStartMonthService {

    private final BranchStartMonthRepository branchStartMonthRepository;

    @Autowired
    public BranchStartMonthService(BranchStartMonthRepository branchStartMonthRepository) {
        this.branchStartMonthRepository = branchStartMonthRepository;
    }

    public List<BranchStartMonthEntity> getAllBranchStartMonthEntities() {
        return branchStartMonthRepository.findAll();
    }

    public List<BranchStartMonthEntity> getAllBranchStartMonthEntitiesOrdered() {
        return branchStartMonthRepository.findBranchStartMonthEntitiesByNameIsNotNullOrderById();
    }

    public void updateBranchStartMonthEntity(BranchStartMonthEntity branchStartMonthEntity) {
        branchStartMonthRepository.save(branchStartMonthEntity);
    }

    public int getBranchStartMonth(String branchName) {
        BranchStartMonthEntity branchStartMonthEntity = branchStartMonthRepository.findFirstByName(branchName);
        return branchStartMonthEntity.getStartMonth();
    }
}
