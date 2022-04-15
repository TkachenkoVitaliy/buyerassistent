package ru.tkachenko.buyerassistent.entity;

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
    private String sell_type;

    @Column(name = "client")//5
    private String client;

    @Column(name = "consignee")//6
    private String consignee;

    @Column(name = "product_type")//7
    private String product_type;

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
    private int accept_month;

    @Column(name = "year")//16
    private int year;

    @Column(name = "accepted")//17
    private double accepted;

    @Column(name = "price")//18
    private double price;

    @Column(name = "accepted_cost")//19
    private double accepted_cost;

    @Column(name = "shipped")//20
    private double shipped;

    @Column(name = "shipped_cost")//21
    private double shipped_cost;

    @Column(name = "shipped_date")//22
    private Date shipped_date;

    @Column(name = "vehicle_number")//23
    private String vehicle_number;

    @Column(name = "invoice_number")//24
    private int invoice_number;

    @Column(name = "invoice_date")//25
    private Date invoice_date;

    @Column(name = "final_price")//26
    private double final_price;

    @Column(name = "final_cost")//27
    private double final_cost;

    public SummaryRowEntity(String supplier, int mill, String branch, String sell_type, String client, String consignee,
                            String product_type, String profile, String grade, String ral, double issued,
                            String contract, String spec, int position, int accept_month, int year, double accepted,
                            double price, double accepted_cost, double shipped, double shipped_cost, Date shipped_date,
                            String vehicle_number, int invoice_number, Date invoice_date, double final_price,
                            double final_cost) {
        this.supplier = supplier;
        this.mill = mill;
        this.branch = branch;
        this.sell_type = sell_type;
        this.client = client;
        this.consignee = consignee;
        this.product_type = product_type;
        this.profile = profile;
        this.grade = grade;
        this.ral = ral;
        this.issued = issued;
        this.contract = contract;
        this.spec = spec;
        this.position = position;
        this.accept_month = accept_month;
        this.year = year;
        this.accepted = accepted;
        this.price = price;
        this.accepted_cost = accepted_cost;
        this.shipped = shipped;
        this.shipped_cost = shipped_cost;
        this.shipped_date = shipped_date;
        this.vehicle_number = vehicle_number;
        this.invoice_number = invoice_number;
        this.invoice_date = invoice_date;
        this.final_price = final_price;
        this.final_cost = final_cost;
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

    public String getSell_type() {
        return sell_type;
    }

    public String getClient() {
        return client;
    }

    public String getConsignee() {
        return consignee;
    }

    public String getProduct_type() {
        return product_type;
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

    public int getAccept_month() {
        return accept_month;
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

    public double getAccepted_cost() {
        return accepted_cost;
    }

    public double getShipped() {
        return shipped;
    }

    public double getShipped_cost() {
        return shipped_cost;
    }

    public Date getShipped_date() {
        return shipped_date;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public int getInvoice_number() {
        return invoice_number;
    }

    public Date getInvoice_date() {
        return invoice_date;
    }

    public double getFinal_price() {
        return final_price;
    }

    public double getFinal_cost() {
        return final_cost;
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

    public void setSell_type(String sell_type) {
        this.sell_type = sell_type;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
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

    public void setAccept_month(int accept_month) {
        this.accept_month = accept_month;
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

    public void setAccepted_cost(double accepted_cost) {
        this.accepted_cost = accepted_cost;
    }

    public void setShipped(double shipped) {
        this.shipped = shipped;
    }

    public void setShipped_cost(double shipped_cost) {
        this.shipped_cost = shipped_cost;
    }

    public void setShipped_date(Date shipped_date) {
        this.shipped_date = shipped_date;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public void setInvoice_number(int invoice_number) {
        this.invoice_number = invoice_number;
    }

    public void setInvoice_date(Date invoice_date) {
        this.invoice_date = invoice_date;
    }

    public void setFinal_price(double final_price) {
        this.final_price = final_price;
    }

    public void setFinal_cost(double final_cost) {
        this.final_cost = final_cost;
    }
}
