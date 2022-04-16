package ru.tkachenko.buyerassistent.summary.dependency_inner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistent.summary.dependency_inner.entity.DependencyEntity;

import java.util.List;

@Repository
public interface DependencyRepository extends JpaRepository<DependencyEntity, Long> {

    @Modifying
    @Query(value = "truncate table dependency_table", nativeQuery = true)
    void truncateTable();

    List<DependencyEntity> findBySpecAndPriority(String spec, int priority);

    List<DependencyEntity> findByConsigneeAndStationAndPriority(String consignee, String station, int priority);

    List<DependencyEntity> findByConsigneeAndPriority(String consignee, int priority);
}
