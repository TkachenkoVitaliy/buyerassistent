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

    public void saveMonthSettings(List<Integer> values) {
        List<BranchStartMonthEntity> branchStartMonthEntities = getAllBranchStartMonthEntitiesOrdered();
        for(int i = 0; i < branchStartMonthEntities.size(); i++) {
            BranchStartMonthEntity branchStartMonthEntity = branchStartMonthEntities.get(i);
            branchStartMonthEntity.setStartMonth(values.get(i));
            updateBranchStartMonthEntity(branchStartMonthEntity);
        }
    }

    public List<BranchStartMonthEntity> getAllBranchStartMonthEntitiesOrdered() {
        return branchStartMonthRepository.findBranchStartMonthEntitiesByNameIsNotNullOrderById();
    }

    private void updateBranchStartMonthEntity(BranchStartMonthEntity branchStartMonthEntity) {
        branchStartMonthRepository.save(branchStartMonthEntity);
    }

    public int getBranchStartMonth(String branchName) {
        BranchStartMonthEntity branchStartMonthEntity = branchStartMonthRepository.findFirstByName(branchName);
        return branchStartMonthEntity.getStartMonth();
    }

}
