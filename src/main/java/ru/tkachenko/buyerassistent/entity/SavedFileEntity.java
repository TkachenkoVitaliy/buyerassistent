package ru.tkachenko.buyerassistent.entity;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "saved_files_table")
public class SavedFileEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "storage_file_name")
    private String storageFileName;

    @Column(name = "saved_timestamp")
    private Timestamp savedTimestamp;

    @Column(name = "year")
    private String year;

    @Column(name = "month")
    private String month;

    @Column(name = "day")
    private String day;

    @Column(name = "time")
    private String time;

    @Column(name = "is_actual")
    private boolean isActual;

    public SavedFileEntity(String filePath, String storageFileName, Timestamp savedTimestamp, String year, String month,
                           String day, String time, boolean isActual) {
        this.filePath = filePath;
        this.storageFileName = storageFileName;
        this.savedTimestamp = savedTimestamp;
        this.year = year;
        this.month = month;
        this.day = day;
        this.time = time;
        this.isActual = isActual;
    }

    public Long getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getStorageFileName() {
        return storageFileName;
    }

    public Timestamp getSavedTimestamp() {
        return savedTimestamp;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public boolean isActual() {
        return isActual;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setStorageFileName(String storageFileName) {
        this.storageFileName = storageFileName;
    }

    public void setSavedTimestamp(Timestamp savedTimestamp) {
        this.savedTimestamp = savedTimestamp;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setActual(boolean actual) {
        isActual = actual;
    }
}
