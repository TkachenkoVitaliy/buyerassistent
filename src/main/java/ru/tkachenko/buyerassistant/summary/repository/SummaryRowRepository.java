package ru.tkachenko.buyerassistant.summary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowEntity;

import java.util.List;

@Repository
public interface SummaryRowRepository extends JpaRepository<SummaryRowEntity, Long> {

    @Modifying
    @Query(value = "truncate table summary_table", nativeQuery = true)
    void truncateTable();

    List<SummaryRowEntity> findAll();

    @Query(value = "SELECT * FROM summary_table " +
            "row WHERE row.branch = :branch AND row.accept_month = :acceptMonth " +
            "ORDER BY supplier, product_type, spec, position;", nativeQuery = true)
    List<SummaryRowEntity> findByBranchAndAcceptMonthOrderBySupplierProductTypeSpecPosition(
            @Param("branch") String branch,
            @Param ("acceptMonth") int acceptMonth);

    List<SummaryRowEntity> findByAcceptMonth(int acceptMonth);

    List<SummaryRowEntity> findBySpec(String spec);

    SummaryRowEntity findFirstBySpecAndAcceptMonthGreaterThan(String spec, int acceptMonth);
}
