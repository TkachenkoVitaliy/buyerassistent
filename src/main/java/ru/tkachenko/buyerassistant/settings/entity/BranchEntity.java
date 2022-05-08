package ru.tkachenko.buyerassistant.settings.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "branches_settings_table")
public class BranchEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_month")
    private int startMonth;

    public BranchEntity(Long id, String name, int startMonth) {
        this.id = id;
        this.name = name;
        this.startMonth = startMonth;
    }

    public BranchEntity() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    @Override
    public String toString() {
        return "BranchEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startMonth=" + startMonth +
                '}';
    }
}
