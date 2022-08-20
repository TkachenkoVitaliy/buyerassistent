package ru.tkachenko.buyerassistant.summary.entity;

import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Immutable
@Table(name = "summary_table")
public class SummaryRowMinEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "mill")
    private int mill;

    @Column(name = "branch")
    private String branch;

    @Column(name = "sell_type")
    private String sellType;

    @Column(name = "consignee")
    private String consignee;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "profile")
    private String profile;

    @Column(name = "grade")
    private String grade;

    @Column(name = "spec")
    private String spec;

    @Column(name = "position")
    private int position;

    @Column(name = "accept_month")
    private int acceptMonth;

    @Column(name = " year")
    private int acceptYear;

    @Column(name = "accepted")
    private double accepted;

    @Column(name = "shipped")
    private double shipped;

    @Column(name = "shipped_date")
    private Date shippedDate;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "price")
    private double price;

    @Column(name = "final_price")
    private double finalPrice;

    public SummaryRowMinEntity() {
    }

    public SummaryRowMinEntity(Long id, String supplier, int mill, String branch, String sellType, String consignee, String productType, String profile, String grade, String spec, int position, int acceptMonth, int acceptYear, double accepted, double shipped, Date shippedDate, String vehicleNumber, double price, double finalPrice) {
        this.id = id;
        this.supplier = supplier;
        this.mill = mill;
        this.branch = branch;
        this.sellType = sellType;
        this.consignee = consignee;
        this.productType = productType;
        this.profile = profile;
        this.grade = grade;
        this.spec = spec;
        this.position = position;
        this.acceptMonth = acceptMonth;
        this.acceptYear = acceptYear;
        this.accepted = accepted;
        this.shipped = shipped;
        this.shippedDate = shippedDate;
        this.vehicleNumber = vehicleNumber;
        this.price = price;
        this.finalPrice = finalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getMill() {
        return mill;
    }

    public void setMill(int mill) {
        this.mill = mill;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getAcceptMonth() {
        return acceptMonth;
    }

    public void setAcceptMonth(int acceptMonth) {
        this.acceptMonth = acceptMonth;
    }

    public int getAcceptYear() {
        return acceptYear;
    }

    public void setAcceptYear(int acceptYear) {
        this.acceptYear = acceptYear;
    }

    public double getAccepted() {
        return accepted;
    }

    public void setAccepted(double accepted) {
        this.accepted = accepted;
    }

    public double getShipped() {
        return shipped;
    }

    public void setShipped(double shipped) {
        this.shipped = shipped;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
