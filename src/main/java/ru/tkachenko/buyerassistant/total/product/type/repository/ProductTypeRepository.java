package ru.tkachenko.buyerassistant.total.product.type.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistant.total.product.group.entity.ProductGroupEntity;
import ru.tkachenko.buyerassistant.total.product.type.entity.ProductTypeEntity;

import java.util.List;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity, Long> {

    List<ProductTypeEntity> findAll();

    ProductTypeEntity findByName(String name);

    List<ProductTypeEntity> findAllByProductGroupOrderById(ProductGroupEntity productGroup);
}
