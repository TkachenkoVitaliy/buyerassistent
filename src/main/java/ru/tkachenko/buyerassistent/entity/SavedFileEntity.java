package ru.tkachenko.buyerassistent.entity;


import javax.persistence.*;

@Entity
@Table(name = "saved_files_table")
public class SavedFileEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /*
    id    BIGINT PRIMARY KEY ,
    file_path VARCHAR (255) NOT NULL ,
    storage_file_name  VARCHAR(150) NOT NULL ,
    saved_timestamp TIMESTAMP(0) NOT NULL ,
    year  VARCHAR(4) NOT NULL ,
    month  VARCHAR(2) NOT NULL ,
    day  VARCHAR(2) NOT NULL ,
    time  VARCHAR(5) NOT NULL ,
    is_actual BOOLEAN
    */
}
