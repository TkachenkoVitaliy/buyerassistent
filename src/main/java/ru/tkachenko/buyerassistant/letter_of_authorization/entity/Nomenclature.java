package ru.tkachenko.buyerassistant.letter_of_authorization.entity;

import javax.persistence.*;

@Entity
@Table(name = "nomenclatures_table")
public class Nomenclature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Nomenclature() {
    }

    public Nomenclature(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
