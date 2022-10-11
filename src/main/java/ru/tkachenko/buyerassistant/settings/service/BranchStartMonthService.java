package ru.tkachenko.buyerassistant.settings.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.settings.entity.BranchStartMonthEntity;
import ru.tkachenko.buyerassistant.settings.repository.BranchStartMonthRepository;
import ru.tkachenko.buyerassistant.summary.service.SummaryDBService;
import ru.tkachenko.buyerassistant.summary.service.SummaryService;
import ru.tkachenko.buyerassistant.utils.CurrentDate;

import java.util.Arrays;
import java.util.List;

@Service
public class BranchStartMonthService {

    private final BranchStartMonthRepository branchStartMonthRepository;
    private final SummaryDBService summaryDBService;

    @Autowired
    public BranchStartMonthService(BranchStartMonthRepository branchStartMonthRepository, SummaryDBService summaryDBService) {
        this.branchStartMonthRepository = branchStartMonthRepository;
        this.summaryDBService = summaryDBService;
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
        String[] allBranches = summaryDBService.findAllBranches();
        Arrays.stream(allBranches).forEach(e -> {
            if(branchStartMonthRepository.findFirstByName(e) == null) {
                CurrentDate currentDate = new CurrentDate();
                branchStartMonthRepository.save(new BranchStartMonthEntity(e, 1,
                        currentDate.getYearInt()));
            }
        });
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
