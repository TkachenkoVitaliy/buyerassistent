package ru.tkachenko.buyerassistant.settings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistant.settings.entity.BranchStartMonthEntity;

import java.util.List;

@Repository
public interface BranchStartMonthRepository extends JpaRepository<BranchStartMonthEntity, Long> {

    public List<BranchStartMonthEntity> findBranchStartMonthEntitiesByNameIsNotNullOrderById();
}
