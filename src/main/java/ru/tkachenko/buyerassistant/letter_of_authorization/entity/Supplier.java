package ru.tkachenko.buyerassistant.letter_of_authorization.entity;

import javax.persistence.*;

@Entity
@Table(name = "suppliers_table")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_name")
    private String supplierName;

    public Supplier() {
    }

    public Supplier(Long id, String supplierName) {
        this.id = id;
        this.supplierName = supplierName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
