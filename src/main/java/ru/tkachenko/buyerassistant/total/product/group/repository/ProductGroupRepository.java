package ru.tkachenko.buyerassistant.total.product.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistant.total.product.group.entity.ProductGroupEntity;

import java.util.List;

@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroupEntity, Long> {
    ProductGroupEntity findFirstByName(String name);

    @Query(value = "SELECT * from product_group_table ORDER BY id", nativeQuery = true)
    List<ProductGroupEntity> findAllOrdered();
}
