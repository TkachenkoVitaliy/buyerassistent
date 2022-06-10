package ru.tkachenko.buyerassistant.product.group.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product_group_table")
public class ProductGroupEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public ProductGroupEntity(String productGroup) {
        this.name = productGroup;
    }

    public ProductGroupEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String productGroup) {
        this.name = productGroup;
    }

    @Override
    public String toString() {
        return "ProductGroupEntity{" +
                "id=" + id +
                ", productGroup='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductGroupEntity that = (ProductGroupEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
