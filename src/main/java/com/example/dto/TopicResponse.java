package com.example.dto;

import com.example.entity.Course;
import com.example.entity.Vocabulary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author BAO 7/7/2023
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TopicResponse {

    private int id;

    private String titleEn;

    private String titleVn;


    private Course course;


    private List<Vocabulary> vocabulary;



}
