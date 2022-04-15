package ru.tkachenko.buyerassistent.dto;

import java.sql.Date;

public class OracleDTO {
    private String mill;
    private String consignee;
    private String productType;
    private String profile;
    private String grade;
    private String ral;
    private String contract;
    private String spec;
    private int position;
    private int accept_month;
    private double accepted;
    private double shipped;
    private Date shipped_date;
    private String vehicle_number;
    private int invoice_number;
    private Date invoice_date;

    private double priceWithoutNDS;
    private String prt;
    private String station;
    private double thickness;
    private double width;
    private double length;
    private String additionalRequirements;

    public OracleDTO(String mill, String consignee, String productType, String profile, String grade, String ral,
                     String contract, String spec, int position, int accept_month, double accepted, double shipped,
                     Date shipped_date, String vehicle_number, int invoice_number, Date invoice_date,
                     double priceWithoutNDS, String prt, String station, double thickness, double width, double length,
                     String additionalRequirements) {
        this.mill = mill;
        this.consignee = consignee;
        this.productType = productType;
        this.profile = profile;
        this.grade = grade;
        this.ral = ral;
        this.contract = contract;
        this.spec = spec;
        this.position = position;
        this.accept_month = accept_month;
        this.accepted = accepted;
        this.shipped = shipped;
        this.shipped_date = shipped_date;
        this.vehicle_number = vehicle_number;
        this.invoice_number = invoice_number;
        this.invoice_date = invoice_date;
        this.priceWithoutNDS = priceWithoutNDS;
        this.prt = prt;
        this.station = station;
        this.thickness = thickness;
        this.width = width;
        this.length = length;
        this.additionalRequirements = additionalRequirements;
    }

    public OracleDTO() {
    }

    public String getMill() {
        return mill;
    }

    public void setMill(String mill) {
        this.mill = mill;
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

    public String getRal() {
        return ral;
    }

    public void setRal(String ral) {
        this.ral = ral;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
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

    public int getAccept_month() {
        return accept_month;
    }

    public void setAccept_month(int accept_month) {
        this.accept_month = accept_month;
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

    public Date getShipped_date() {
        return shipped_date;
    }

    public void setShipped_date(Date shipped_date) {
        this.shipped_date = shipped_date;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public int getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(int invoice_number) {
        this.invoice_number = invoice_number;
    }

    public Date getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(Date invoice_date) {
        this.invoice_date = invoice_date;
    }

    public double getPriceWithoutNDS() {
        return priceWithoutNDS;
    }

    public void setPriceWithoutNDS(double priceWithoutNDS) {
        this.priceWithoutNDS = priceWithoutNDS;
    }

    public String getPrt() {
        return prt;
    }

    public void setPrt(String prt) {
        this.prt = prt;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getAdditionalRequirements() {
        return additionalRequirements;
    }

    public void setAdditionalRequirements(String additionalRequirements) {
        this.additionalRequirements = additionalRequirements;
    }
}
