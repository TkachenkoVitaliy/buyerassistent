package ru.tkachenko.buyerassistant.mmk_accept.entity;

import javax.persistence.*;

@Entity
@Table(name = "accept_table")

public class MmkAcceptRowEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "spec")
    private String spec;

    @Column(name = "position")
    private int position;

    @Column(name = "nomenclature")
    private String nomenclature;

    @Column(name = "grade")
    private String grade;

    @Column(name = "thickness")
    private double thickness;

    @Column(name = "width")
    private double width;

    @Column(name = "length")
    private double length;

    @Column(name = "alter_profile")
    private String alterProfile;

    @Column(name = "accepted")
    private double accepted;

    @Column(name = "accept_month")
    private int acceptMonth;

    @Column(name = "additional_requirements")
    private String additionalRequirements;

    public MmkAcceptRowEntity(String spec, int position, String nomenclature, String grade, double thickness,
                              double width, double length, String alterProfile, double accepted, int acceptMonth,
                              String additionalRequirements) {
        this.spec = spec;
        this.position = position;
        this.nomenclature = nomenclature;
        this.grade = grade;
        this.thickness = thickness;
        this.width = width;
        this.length = length;
        this.alterProfile = alterProfile;
        this.accepted = accepted;
        this.acceptMonth = acceptMonth;
        this.additionalRequirements = additionalRequirements;
    }

    public MmkAcceptRowEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getSpec() {
        return spec;
    }

    public int getPosition() {
        return position;
    }

    public String getNomenclature() {
        return nomenclature;
    }

    public String getGrade() {
        return grade;
    }

    public double getThickness() {
        return thickness;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public String getAlterProfile() {
        return alterProfile;
    }

    public double getAccepted() {
        return accepted;
    }

    public int getAcceptMonth() {
        return acceptMonth;
    }

    public String getAdditionalRequirements() {
        return additionalRequirements;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setNomenclature(String nomenclature) {
        this.nomenclature = nomenclature;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setAlterProfile(String alterProfile) {
        this.alterProfile = alterProfile;
    }

    public void setAccepted(double accepted) {
        this.accepted = accepted;
    }

    public void setAcceptMonth(int acceptMonth) {
        this.acceptMonth = acceptMonth;
    }

    public void setAdditionalRequirements(String additionalRequirements) {
        this.additionalRequirements = additionalRequirements;
    }
}
