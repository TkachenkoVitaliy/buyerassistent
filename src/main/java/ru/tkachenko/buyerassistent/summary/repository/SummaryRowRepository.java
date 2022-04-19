package ru.tkachenko.buyerassistent.summary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistent.summary.entity.SummaryRowEntity;

import java.util.List;

@Repository
public interface SummaryRowRepository extends JpaRepository<SummaryRowEntity, Long> {

    @Modifying
    @Query(value = "truncate table summary_table", nativeQuery = true)
    void truncateTable();

    List<SummaryRowEntity> findAll();
}
