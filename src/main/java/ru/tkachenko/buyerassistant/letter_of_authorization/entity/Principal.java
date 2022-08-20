package ru.tkachenko.buyerassistant.letter_of_authorization.entity;


import javax.persistence.*;

@Entity
@Table(name = "principals_table")
public class Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String inn;

    private String kpp;

    private String address;

    @Column(name = "bank_account")
    private String bankAccount;

    @Column(name = "director_name")
    private String directorName;

    public Principal() {
    }

    public Principal(String name, String inn, String kpp, String address, String bankAccount, String directorName) {
        this.name = name;
        this.inn = inn;
        this.kpp = kpp;
        this.address = address;
        this.bankAccount = bankAccount;
        this.directorName = directorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }
}
