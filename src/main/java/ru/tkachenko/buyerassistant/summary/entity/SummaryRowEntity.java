package ru.tkachenko.buyerassistant.summary.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "summary_table")
public class SummaryRowEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "supplier")//1
    private String supplier;

    @Column(name = "mill")//2
    private int mill;

    @Column(name = "branch")//3
    private String branch;

    @Column(name = "sell_type")//4
    private String sellType;

    @Column(name = "client")//5
    private String client;

    @Column(name = "consignee")//6
    private String consignee;

    @Column(name = "product_type")//7
    private String productType;

    @Column(name = "profile")//8
    private String profile;

    @Column(name = "grade")//9
    private String grade;

    @Column(name = "ral")//10
    private String ral;

    @Column(name = "issued")//11
    private double issued;

    @Column(name = "contract")//12
    private String contract;

    @Column(name = "spec")//13
    private String spec;

    @Column(name = "position")//14
    private int position;

    @Column(name = "accept_month")//15
    private int acceptMonth;

    @Column(name = "year")//16
    private int year;

    @Column(name = "accepted")//17
    private double accepted;

    @Column(name = "price")//18
    private double price;

    @Column(name = "accepted_cost")//19
    private double acceptedCost;

    @Column(name = "shipped")//20
    private double shipped;

    @Column(name = "shipped_cost")//21
    private double shippedCost;

    @Column(name = "shipped_date")//22
    private Date shippedDate;

    @Column(name = "vehicle_number")//23
    private String vehicleNumber;

    @Column(name = "invoice_number")//24
    private int invoiceNumber;

    @Column(name = "invoice_date")//25
    private Date invoiceDate;

    @Column(name = "final_price")//26
    private double finalPrice;

    @Column(name = "final_cost")//27
    private double finalCost;

    @Column(name = "additional_req")//28
    private String additionalReq;

    public SummaryRowEntity(String supplier, int mill, String branch, String sellType, String client, String consignee,
                            String productType, String profile, String grade, String ral, double issued,
                            String contract, String spec, int position, int acceptMonth, int year, double accepted,
                            double price, double acceptedCost, double shipped, double shippedCost, Date shippedDate,
                            String vehicleNumber, int invoiceNumber, Date invoiceDate, double finalPrice,
                            double finalCost, String additionalReq) {
        this.supplier = supplier;
        this.mill = mill;
        this.branch = branch;
        this.sellType = sellType;
        this.client = client;
        this.consignee = consignee;
        this.productType = productType;
        this.profile = profile;
        this.grade = grade;
        this.ral = ral;
        this.issued = issued;
        this.contract = contract;
        this.spec = spec;
        this.position = position;
        this.acceptMonth = acceptMonth;
        this.year = year;
        this.accepted = accepted;
        this.price = price;
        this.acceptedCost = acceptedCost;
        this.shipped = shipped;
        this.shippedCost = shippedCost;
        this.shippedDate = shippedDate;
        this.vehicleNumber = vehicleNumber;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.finalPrice = finalPrice;
        this.finalCost = finalCost;
        this.additionalReq = additionalReq;
    }

    public SummaryRowEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getSupplier() {
        return supplier;
    }

    public int getMill() {
        return mill;
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

    public String getConsignee() {
        return consignee;
    }

    public String getProductType() {
        return productType;
    }

    public String getProfile() {
        return profile;
    }

    public String getGrade() {
        return grade;
    }

    public String getRal() {
        return ral;
    }

    public double getIssued() {
        return issued;
    }

    public String getContract() {
        return contract;
    }

    public String getSpec() {
        return spec;
    }

    public int getPosition() {
        return position;
    }

    public int getAcceptMonth() {
        return acceptMonth;
    }

    public int getYear() {
        return year;
    }

    public double getAccepted() {
        return accepted;
    }

    public double getPrice() {
        return price;
    }

    public double getAcceptedCost() {
        return acceptedCost;
    }

    public double getShipped() {
        return shipped;
    }

    public double getShippedCost() {
        return shippedCost;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public double getFinalCost() {
        return finalCost;
    }

    public String getAdditionalReq() {
        return additionalReq;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setMill(int mill) {
        this.mill = mill;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setSellType(String sell_type) {
        this.sellType = sell_type;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public void setProductType(String product_type) {
        this.productType = product_type;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setRal(String ral) {
        this.ral = ral;
    }

    public void setIssued(double issued) {
        this.issued = issued;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setAcceptMonth(int accept_month) {
        this.acceptMonth = accept_month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAccepted(double accepted) {
        this.accepted = accepted;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAcceptedCost(double accepted_cost) {
        this.acceptedCost = accepted_cost;
    }

    public void setShipped(double shipped) {
        this.shipped = shipped;
    }

    public void setShippedCost(double shipped_cost) {
        this.shippedCost = shipped_cost;
    }

    public void setShippedDate(Date shipped_date) {
        this.shippedDate = shipped_date;
    }

    public void setVehicleNumber(String vehicle_number) {
        this.vehicleNumber = vehicle_number;
    }

    public void setInvoiceNumber(int invoice_number) {
        this.invoiceNumber = invoice_number;
    }

    public void setInvoiceDate(Date invoice_date) {
        this.invoiceDate = invoice_date;
    }

    public void setFinalPrice(double final_price) {
        this.finalPrice = final_price;
    }

    public void setFinalCost(double final_cost) {
        this.finalCost = final_cost;
    }

    public void setAdditionalReq(String additionalReq) {
        this.additionalReq = additionalReq;
    }

    @Override
    public String toString() {
        return "SummaryRowEntity{" +
                "id=" + id +
                ", supplier='" + supplier + '\'' +
                ", mill=" + mill +
                ", branch='" + branch + '\'' +
                ", sellType='" + sellType + '\'' +
                ", client='" + client + '\'' +
                ", consignee='" + consignee + '\'' +
                ", productType='" + productType + '\'' +
                ", profile='" + profile + '\'' +
                ", grade='" + grade + '\'' +
                ", ral='" + ral + '\'' +
                ", issued=" + issued +
                ", contract='" + contract + '\'' +
                ", spec='" + spec + '\'' +
                ", position=" + position +
                ", acceptMonth=" + acceptMonth +
                ", year=" + year +
                ", accepted=" + accepted +
                ", price=" + price +
                ", acceptedCost=" + acceptedCost +
                ", shipped=" + shipped +
                ", shippedCost=" + shippedCost +
                ", shippedDate=" + shippedDate +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", invoiceNumber=" + invoiceNumber +
                ", invoiceDate=" + invoiceDate +
                ", finalPrice=" + finalPrice +
                ", finalCost=" + finalCost +
                ", additionalReq='" + additionalReq + '\'' +
                '}';
    }
}
