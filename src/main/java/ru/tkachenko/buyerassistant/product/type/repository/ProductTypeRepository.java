package ru.tkachenko.buyerassistant.product.type.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkachenko.buyerassistant.product.type.entity.ProductTypeEntity;

import java.util.List;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity, Long> {
    List<ProductTypeEntity> findAllByGroupId(Long id);
}
