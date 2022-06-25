package ru.tkachenko.buyerassistant.summary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistant.summary.entity.SummaryRowEntity;
import ru.tkachenko.buyerassistant.total.product.type.entity.ProductTypeEntity;

import java.util.List;

@Repository
public interface SummaryRowRepository extends JpaRepository<SummaryRowEntity, Long> {

    @Modifying
    @Query(value = "truncate table summary_table", nativeQuery = true)
    void truncateTable();

    List<SummaryRowEntity> findAll();

    @Query(value = "SELECT * FROM summary_table " +
            "row WHERE row.branch = :branch AND row.accept_month = :acceptMonth AND row.year = :year AND (row.accepted > 1 OR row.shipped > 0)" +
            "ORDER BY supplier, product_type, spec, position, profile, accepted DESC;", nativeQuery = true)
    List<SummaryRowEntity> findByBranchAndAcceptMonthAndYearOrderBySupplierProductTypeSpecPositionProfileAccept(
            @Param("branch") String branch,
            @Param ("acceptMonth") int acceptMonth,
            @Param ("year") int year);

    List<SummaryRowEntity> findByAcceptMonth(int acceptMonth);

    List<SummaryRowEntity> findBySpec(String spec);

    SummaryRowEntity findFirstBySpecAndAcceptMonthGreaterThan(String spec, int acceptMonth);

    @Query(value = "SELECT DISTINCT supplier FROM summary_table WHERE accept_month = :month AND year = :year " +
            "ORDER BY supplier", nativeQuery = true)
    List<String> findUniqueSuppliersByMonthAndYearOrdered(int month, int year);

    @Query(value = "SELECT DISTINCT branch FROM summary_table WHERE accept_month = :month AND year = :year " +
            "AND sell_type = :sellType ORDER BY branch", nativeQuery = true)
    List<String> findUniqueBranchesByMonthAndYearAndSellTypeOrdered(int month, int year, String sellType);

    List<SummaryRowEntity> findAllByAcceptMonthAndYear(int acceptMonth, int year);

    @Query(value = "SELECT DISTINCT product_type FROM summary_table", nativeQuery = true)
    List<String> findAllProductTypeNames();

    @Query(value = "SELECT DISTINCT * FROM summary_table", nativeQuery = true)
    List<ProductTypeEntity> findAllProductTypes();
}
