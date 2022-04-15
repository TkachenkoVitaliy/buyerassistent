package ru.tkachenko.buyerassistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tkachenko.buyerassistent.entity.SummaryRowEntity;

public interface SummaryRowRepository extends JpaRepository<SummaryRowEntity, Long> {
}
