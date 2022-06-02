package ru.tkachenko.buyerassistant.product.group.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product_group_table")
public class ProductGroupEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "product_group")
    private String productGroup;

    public ProductGroupEntity(String productGroup) {
        this.productGroup = productGroup;
    }

    public ProductGroupEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    @Override
    public String toString() {
        return "ProductGroupEntity{" +
                "id=" + id +
                ", productGroup='" + productGroup + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductGroupEntity that = (ProductGroupEntity) o;
        return Objects.equals(productGroup, that.productGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productGroup);
    }
}
