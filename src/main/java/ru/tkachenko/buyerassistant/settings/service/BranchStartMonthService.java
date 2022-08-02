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

    public void saveMonthSettings(List<Integer> values, List<Integer> yearValues) {
        List<BranchStartMonthEntity> branchStartMonthEntities = getAllBranchStartMonthEntitiesOrdered();
        for(int i = 0; i < branchStartMonthEntities.size(); i++) {
            BranchStartMonthEntity branchStartMonthEntity = branchStartMonthEntities.get(i);
            branchStartMonthEntity.setStartMonth(values.get(i));
            branchStartMonthEntity.setStartYear(yearValues.get(i));
            updateBranchStartMonthEntity(branchStartMonthEntity);
        }
    }

    public List<BranchStartMonthEntity> getAllBranchStartMonthEntitiesOrdered() {
        return branchStartMonthRepository.findBranchStartMonthEntitiesByNameIsNotNullOrderById();
    }

    public void updateBranchStartMonthEntity(BranchStartMonthEntity branchStartMonthEntity) {
        branchStartMonthRepository.save(branchStartMonthEntity);
    }

    public int getBranchStartMonth(String branchName) {
        System.out.println(branchName);
        BranchStartMonthEntity branchStartMonthEntity = branchStartMonthRepository.findFirstByName(branchName);
        System.out.println(branchStartMonthEntity);
        return branchStartMonthEntity.getStartMonth();
    }

    public int getBranchStartYear(String branchName) {
        BranchStartMonthEntity branchStartMonthEntity = branchStartMonthRepository.findFirstByName(branchName);
        return branchStartMonthEntity.getStartYear();
    }

}
