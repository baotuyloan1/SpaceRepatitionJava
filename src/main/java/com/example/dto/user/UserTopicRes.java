package com.example.dto.user;

import com.example.dto.TopicResponseTemp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BAO 7/19/2023
 */
@Getter
@Setter
@NoArgsConstructor
public class UserTopicRes extends TopicResponseTemp {
    private boolean learnedAll;

    public UserTopicRes(int id, String titleEn, String titleVn, boolean learnedAll) {
        super(id, titleEn, titleVn);
        this.learnedAll = learnedAll;
    }
}
