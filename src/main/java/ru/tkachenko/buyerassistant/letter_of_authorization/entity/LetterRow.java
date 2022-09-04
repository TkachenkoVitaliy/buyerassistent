package ru.tkachenko.buyerassistant.letter_of_authorization.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "letter_rows_table")
public class LetterRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nomenclature_id")
    private Nomenclature nomenclature;

    private Integer number;

    private BigDecimal tonnage;

    @ManyToOne
    @JoinColumn(name = "letterOfAuthorization_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private LetterOfAuthorization letterOfAuthorization;

    public LetterRow() {
    }

    public LetterRow(Nomenclature nomenclature, Integer number, BigDecimal tonnage, LetterOfAuthorization letterOfAuthorization) {
        this.nomenclature = nomenclature;
        this.number = number;
        this.tonnage = tonnage;
        this.letterOfAuthorization = letterOfAuthorization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nomenclature getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(Nomenclature nomenclature) {
        this.nomenclature = nomenclature;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getTonnage() {
        return tonnage;
    }

    public void setTonnage(BigDecimal tonnage) {
        this.tonnage = tonnage;
    }

    public LetterOfAuthorization getLetterOfAuthorization() {
        return letterOfAuthorization;
    }

    public void setLetterOfAuthorization(LetterOfAuthorization letterOfAuthorization) {
        this.letterOfAuthorization = letterOfAuthorization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LetterRow)) return false;

        LetterRow letterRow = (LetterRow) o;

        if (id != null ? !id.equals(letterRow.id) : letterRow.id != null) return false;
        if (nomenclature != null ? !nomenclature.equals(letterRow.nomenclature) : letterRow.nomenclature != null)
            return false;
        if (number != null ? !number.equals(letterRow.number) : letterRow.number != null) return false;
        if (tonnage != null ? !tonnage.equals(letterRow.tonnage) : letterRow.tonnage != null) return false;
        return letterOfAuthorization != null ? letterOfAuthorization.equals(letterRow.letterOfAuthorization) : letterRow.letterOfAuthorization == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nomenclature != null ? nomenclature.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (tonnage != null ? tonnage.hashCode() : 0);
        result = 31 * result + (letterOfAuthorization != null ? letterOfAuthorization.hashCode() : 0);
        return result;
    }
}
