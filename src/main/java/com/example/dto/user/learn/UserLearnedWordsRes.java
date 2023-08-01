package com.example.dto.user.learn;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ADMIN 8/1/2023
 */
@Getter
@Setter
public class UserLearnedWordsRes {
    private long userId;
    private int totalCountWords;
    private int countWordsLv1;
    private int countWordsLv2;
    private int countWordsLv3;
    private int countWordsLv4;
    private int countWordsLv5;
}
