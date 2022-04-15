package ru.tkachenko.buyerassistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistent.entity.DependencyEntity;

@Repository
public interface DependencyRepository extends JpaRepository<DependencyEntity, Long> {

    @Modifying
    @Query(value = "truncate table summary_table", nativeQuery = true)
    void truncateTable();
}
