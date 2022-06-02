package ru.tkachenko.buyerassistant.product.type.entity;

import ru.tkachenko.buyerassistant.product.group.entity.ProductGroupEntity;

import javax.persistence.*;

@Entity
@Table(name = "product_type_table")
public class ProductTypeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "product_group_id")
    private Long groupId;

    @ManyToOne(targetEntity = ProductGroupEntity.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "product_group")
    private ProductGroupEntity productGroupEntity;

    public ProductTypeEntity(String productType, Long groupId) {
        this.productType = productType;
        this.groupId = groupId;
    }

    public ProductTypeEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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
                ", productType='" + productType + '\'' +
                ", groupId=" + groupId +
                ", productGroupEntity=" + productGroupEntity +
                '}';
    }
}
