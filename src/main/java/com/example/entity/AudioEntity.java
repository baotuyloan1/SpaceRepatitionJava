package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author BAO
 * 6/29/2023
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "audioUpload")
public class AudioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 50)
    private String title;
    @Column(length = 200)
    private String description;
    private String tags;
    private String audioName;

    private Date addedDate;

}
