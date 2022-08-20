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
}
