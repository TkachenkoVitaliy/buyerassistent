package ru.tkachenko.buyerassistant.settings.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.settings.entity.BranchEntity;
import ru.tkachenko.buyerassistant.settings.repository.BranchRepository;

import java.util.List;

@Service
public class BranchService {

    private final BranchRepository branchRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public List<BranchEntity> getAllBranchEntities() {
        return branchRepository.findAll();
    }
}
