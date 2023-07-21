package com.example.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BAO 7/21/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCourseRes {
  private int id;
  private String img;
  private String title;
  private String target;
  private String description;
}
