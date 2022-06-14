package ru.tkachenko.buyerassistant.product.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistant.product.group.entity.ProductGroupEntity;

@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroupEntity, Long> {
    ProductGroupEntity findFirstByName(String name);


}
