package ru.tkachenko.buyerassistant.settings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistant.settings.entity.BranchEntity;

@Repository
public interface BranchRepository extends JpaRepository<BranchEntity, Long> {

}
