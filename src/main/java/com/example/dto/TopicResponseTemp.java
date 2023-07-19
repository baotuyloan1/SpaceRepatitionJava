package com.example.dto;

import com.example.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BAO 7/19/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class TopicResponseTemp {
  private int id;
  private String titleEn;
  private String titleVn;


}
