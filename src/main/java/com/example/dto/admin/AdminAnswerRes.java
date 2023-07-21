package com.example.dto.admin;

import com.example.entity.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BAO 7/21/2023
 */
@Getter
@Setter
@NoArgsConstructor
public class AdminAnswerRes {
  private Long id;
  private String answer;
  }
