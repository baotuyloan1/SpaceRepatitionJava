package com.example.dto.admin;

import com.example.entity.Course;
import com.example.entity.Vocabulary;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class AdminTopicRes {
    private int id;
    private String titleEn;
    private String titleVn;
    private AdminCourseRes course;
    private int numberWords;
    private String img;


}
