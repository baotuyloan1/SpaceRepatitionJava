package com.example.dto.user.review;

import com.example.dto.user.TypeLearnRes;
import com.example.dto.user.learn.UserLearnRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

/**
 * @author ADMIN 7/31/2023
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserNextWordsReq extends UserLearnRes {

  private Date submitDate;
  private Date reviewDate;


}
