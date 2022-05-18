package ru.tkachenko.buyerassistant.email.entity;

import javax.persistence.*;

@Entity
@Table(name="mail_table")
public class MailEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "email_address")
    private String emailAddress;

    public MailEntity(Long id, String branchName, String emailAddress) {
        this.id = id;
        this.branchName = branchName;
        this.emailAddress = emailAddress;
    }

    public MailEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "MailEntity{" +
                "id=" + id +
                ", branchName='" + branchName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
