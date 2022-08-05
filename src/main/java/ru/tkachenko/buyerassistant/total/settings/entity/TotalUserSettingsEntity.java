package ru.tkachenko.buyerassistant.total.settings.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name="total_settings")
public class TotalUserSettingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("month")
    private Integer month;

    @JsonProperty("year")
    private Integer year;

    public TotalUserSettingsEntity(Long id, String username, Integer month, Integer year) {
        this.id = id;
        this.username = username;
        this.month = month;
        this.year = year;
    }

    public TotalUserSettingsEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "TotalUserSettingsEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}
