package ru.tkachenko.buyerassistent.dto;

import java.sql.Date;

public class OracleDTO {
    private int mill;
    private String consignee;
    private String productType;
    private String profile;
    private String grade;
    private String ral;
    private String contract;
    private String spec;
    private int position;
    private int acceptMonth;
    private double accepted;
    private double shipped;
    private Date shippedDate;
    private String vehicleNumber;
    private int invoiceNumber;
    private Date invoiceDate;

    private double priceWithoutNDS;
    private String prt;
    private String station;
    private double thickness;
    private double width;
    private double length;
    private String additionalRequirements;

    public OracleDTO(int mill, String consignee, String productType, String profile, String grade, String ral,
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
        this.acceptMonth = accept_month;
        this.accepted = accepted;
        this.shipped = shipped;
        this.shippedDate = shipped_date;
        this.vehicleNumber = vehicle_number;
        this.invoiceNumber = invoice_number;
        this.invoiceDate = invoice_date;
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

    public int getMill() {
        return mill;
    }

    public void setMill(int mill) {
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

    public int getAcceptMonth() {
        return acceptMonth;
    }

    public void setAcceptMonth(int acceptMonth) {
        this.acceptMonth = acceptMonth;
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

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
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

    @Override
    public String toString() {
        return "OracleDTO{" +
                "mill='" + mill + '\'' +
                ", consignee='" + consignee + '\'' +
                ", productType='" + productType + '\'' +
                ", profile='" + profile + '\'' +
                ", grade='" + grade + '\'' +
                ", ral='" + ral + '\'' +
                ", contract='" + contract + '\'' +
                ", spec='" + spec + '\'' +
                ", position=" + position +
                ", acceptMonth=" + acceptMonth +
                ", accepted=" + accepted +
                ", shipped=" + shipped +
                ", shippedDate=" + shippedDate +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", invoiceNumber=" + invoiceNumber +
                ", invoiceDate=" + invoiceDate +
                ", priceWithoutNDS=" + priceWithoutNDS +
                ", prt='" + prt + '\'' +
                ", station='" + station + '\'' +
                ", thickness=" + thickness +
                ", width=" + width +
                ", length=" + length +
                ", additionalRequirements='" + additionalRequirements + '\'' +
                '}';
    }
}
