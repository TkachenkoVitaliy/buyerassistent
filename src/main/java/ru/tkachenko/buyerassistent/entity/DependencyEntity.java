package ru.tkachenko.buyerassistent.entity;

import javax.persistence.*;

@Entity
@Table(name = "dependency_table")
public class DependencyEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "consignee")
    private String consignee;

    @Column(name = "station")
    private String station;

    @Column(name = "spec")
    private String spec;

    @Column(name = "position")
    private int position;

    @Column(name = "branch")
    private String branch;

    @Column(name = "sell_type")
    private String sellType;

    @Column(name = "client")
    private String client;

    @Column(name = "priority")
    private int priority;

    public DependencyEntity(String consignee, String station, String spec, int position, String branch, String sellType,
                            String client, int priority) {
        this.consignee = consignee;
        this.station = station;
        this.spec = spec;
        this.position = position;
        this.branch = branch;
        this.sellType = sellType;
        this.client = client;
        this.priority = priority;
    }

    public DependencyEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getConsignee() {
        return consignee;
    }

    public String getStation() {
        return station;
    }

    public String getSpec() {
        return spec;
    }

    public int getPosition() {
        return position;
    }

    public String getBranch() {
        return branch;
    }

    public String getSellType() {
        return sellType;
    }

    public String getClient() {
        return client;
    }

    public int getPriority() {
        return priority;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
