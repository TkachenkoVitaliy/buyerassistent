package ru.tkachenko.buyerassistant.letter_of_authorization.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "drivers_table")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "passport_series")
    private String passportSeries;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "issued_by")
    private String issuedBy;

    @Column(name = "date_of_issue")
    private Date dateOfIssue;

    public Driver() {
    }

    public Driver(String name, String passportSeries, String passportNumber, String issuedBy, Date dateOfIssue) {
        this.name = name;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
        this.issuedBy = issuedBy;
        this.dateOfIssue = dateOfIssue;
    }

    public Driver(Long id, String name, String passportSeries, String passportNumber, String issuedBy, Date dateOfIssue) {
        this.id = id;
        this.name = name;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
        this.issuedBy = issuedBy;
        this.dateOfIssue = dateOfIssue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driver)) return false;

        Driver driver = (Driver) o;

        if (id != null ? !id.equals(driver.id) : driver.id != null) return false;
        if (name != null ? !name.equals(driver.name) : driver.name != null) return false;
        if (passportSeries != null ? !passportSeries.equals(driver.passportSeries) : driver.passportSeries != null)
            return false;
        if (passportNumber != null ? !passportNumber.equals(driver.passportNumber) : driver.passportNumber != null)
            return false;
        if (issuedBy != null ? !issuedBy.equals(driver.issuedBy) : driver.issuedBy != null) return false;
        return dateOfIssue != null ? dateOfIssue.equals(driver.dateOfIssue) : driver.dateOfIssue == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (passportSeries != null ? passportSeries.hashCode() : 0);
        result = 31 * result + (passportNumber != null ? passportNumber.hashCode() : 0);
        result = 31 * result + (issuedBy != null ? issuedBy.hashCode() : 0);
        result = 31 * result + (dateOfIssue != null ? dateOfIssue.hashCode() : 0);
        return result;
    }
}
