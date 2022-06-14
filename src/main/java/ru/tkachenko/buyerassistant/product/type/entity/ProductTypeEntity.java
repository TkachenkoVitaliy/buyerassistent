package ru.tkachenko.buyerassistant.product.type.entity;

import ru.tkachenko.buyerassistant.product.group.entity.ProductGroupEntity;

import javax.persistence.*;

@Entity
@Table(name = "product_type_table")
public class ProductTypeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private ProductGroupEntity productGroup;


    public ProductTypeEntity(Long id, String name, ProductGroupEntity productGroup) {
        this.id = id;
        this.name = name;
        this.productGroup = productGroup;
    }

    public ProductTypeEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String productType) {
        this.name = productType;
    }

    public ProductGroupEntity getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroupEntity productGroupEntity) {
        this.productGroup = productGroupEntity;
    }

    @Override
    public String toString() {
        return "ProductTypeEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productGroupEntity=" + productGroup +
                '}';
    }
}
