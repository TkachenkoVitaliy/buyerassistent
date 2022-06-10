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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="product_group_table")
    @JoinColumn(name="id")
    private ProductGroupEntity productGroupEntity;


    public ProductTypeEntity(Long id, String name, ProductGroupEntity productGroupEntity) {
        this.id = id;
        this.name = name;
        this.productGroupEntity = productGroupEntity;
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

    public ProductGroupEntity getProductGroupEntity() {
        return productGroupEntity;
    }

    public void setProductGroupEntity(ProductGroupEntity productGroupEntity) {
        this.productGroupEntity = productGroupEntity;
    }

    @Override
    public String toString() {
        return "ProductTypeEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productGroupEntity=" + productGroupEntity +
                '}';
    }
}
