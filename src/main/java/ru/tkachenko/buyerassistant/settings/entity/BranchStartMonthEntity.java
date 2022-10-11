package ru.tkachenko.buyerassistant.settings.entity;

import javax.persistence.*;

@Entity
@Table(name = "branches_settings_table")
public class BranchStartMonthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_month")
    private int startMonth;

    @Column(name = "start_year")
    private int startYear;

    public BranchStartMonthEntity(Long id, String name, int startMonth, int startYear) {
        this.id = id;
        this.name = name;
        this.startMonth = startMonth;
        this.startYear = startYear;
    }

    public BranchStartMonthEntity(String name, int startMonth, int startYear) {
        this.name = name;
        this.startMonth = startMonth;
        this.startYear = startYear;
    }

    public BranchStartMonthEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public int getStartYear() {return startYear;}

    public void setName(String name) {
        this.name = name;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public void setStartYear(int startYear) {this.startYear = startYear;}

    @Override
    public String toString() {
        return "BranchStartMonthEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startMonth=" + startMonth +
                ", startYear=" + startYear +
                '}';
    }
}
