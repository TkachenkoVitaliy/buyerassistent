package ru.tkachenko.buyerassistant.summary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowMinEntity;


public interface SummaryRowMinRepository extends JpaRepository<SummaryRowMinEntity,Long> {
}
