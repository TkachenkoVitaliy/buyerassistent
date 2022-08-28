package ru.tkachenko.buyerassistant.letter_of_authorization.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Letters_of_authorization_table")
public class LetterOfAuthorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "principal_id", nullable=false)
    private Principal principal;

    private Integer number;

    private Date issuedDate;

    private Date validUntil;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable=false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name="driver_id", nullable=false)
    private Driver driver;

    @OneToMany(mappedBy = "letterOfAuthorization")
    @JsonManagedReference
    private List<LetterRow> letterRows;

    private String sellType;

    public LetterOfAuthorization() {
    }

    public LetterOfAuthorization(Principal principal, Integer number, Date issuedDate, Date validUntil, Supplier supplier, Driver driver, List<LetterRow> letterRows, String sellType) {
        this.principal = principal;//
        this.number = number;//
        this.issuedDate = issuedDate;//
        this.validUntil = validUntil;
        this.supplier = supplier;//
        this.driver = driver;//
        this.letterRows = letterRows;
        this.sellType = sellType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<LetterRow> getLetterRows() {
        return letterRows;
    }

    public void setLetterRows(List<LetterRow> letterRows) {
        this.letterRows = letterRows;
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }
}
