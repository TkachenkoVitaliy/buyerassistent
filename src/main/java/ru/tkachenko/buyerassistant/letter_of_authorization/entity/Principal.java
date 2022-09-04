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

    private String okpo;

    private String address;

    @Column(name = "bank_account")
    private String bankAccount;

    @Column(name = "director_name")
    private String directorName;

    public Principal() {
    }

    public Principal(String name, String inn, String kpp, String okpo, String address, String bankAccount, String directorName) {
        this.name = name;
        this.inn = inn;
        this.kpp = kpp;
        this.okpo = okpo;
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

    public String getOkpo() {
        return okpo;
    }

    public void setOkpo(String okpo) {
        this.okpo = okpo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Principal)) return false;

        Principal principal = (Principal) o;

        if (id != null ? !id.equals(principal.id) : principal.id != null) return false;
        if (name != null ? !name.equals(principal.name) : principal.name != null) return false;
        if (inn != null ? !inn.equals(principal.inn) : principal.inn != null) return false;
        if (kpp != null ? !kpp.equals(principal.kpp) : principal.kpp != null) return false;
        if (okpo != null ? !okpo.equals(principal.okpo) : principal.okpo != null) return false;
        if (address != null ? !address.equals(principal.address) : principal.address != null) return false;
        if (bankAccount != null ? !bankAccount.equals(principal.bankAccount) : principal.bankAccount != null)
            return false;
        return directorName != null ? directorName.equals(principal.directorName) : principal.directorName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (inn != null ? inn.hashCode() : 0);
        result = 31 * result + (kpp != null ? kpp.hashCode() : 0);
        result = 31 * result + (okpo != null ? okpo.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (bankAccount != null ? bankAccount.hashCode() : 0);
        result = 31 * result + (directorName != null ? directorName.hashCode() : 0);
        return result;
    }
}
