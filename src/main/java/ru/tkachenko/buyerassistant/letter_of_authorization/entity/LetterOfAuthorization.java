package ru.tkachenko.buyerassistant.letter_of_authorization.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LetterOfAuthorization)) return false;

        LetterOfAuthorization that = (LetterOfAuthorization) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (principal != null ? !principal.equals(that.principal) : that.principal != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (issuedDate != null ? !issuedDate.equals(that.issuedDate) : that.issuedDate != null) return false;
        if (validUntil != null ? !validUntil.equals(that.validUntil) : that.validUntil != null) return false;
        if (supplier != null ? !supplier.equals(that.supplier) : that.supplier != null) return false;
        if (driver != null ? !driver.equals(that.driver) : that.driver != null) return false;
        if (letterRows != null ? !letterRows.equals(that.letterRows) : that.letterRows != null) return false;
        return sellType != null ? sellType.equals(that.sellType) : that.sellType == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (principal != null ? principal.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (issuedDate != null ? issuedDate.hashCode() : 0);
        result = 31 * result + (validUntil != null ? validUntil.hashCode() : 0);
        result = 31 * result + (supplier != null ? supplier.hashCode() : 0);
        result = 31 * result + (driver != null ? driver.hashCode() : 0);
        result = 31 * result + (letterRows != null ? letterRows.hashCode() : 0);
        result = 31 * result + (sellType != null ? sellType.hashCode() : 0);
        return result;
    }
}
