package com.example.mapper;

import com.example.dto.admin.AdminQuestionRes;
import com.example.dto.user.UserQuestionResponse;
import com.example.entity.Question;

/**
 * @author BAO 7/20/2023
 */
public interface QuestionMapper {

    UserQuestionResponse questionToUserQuestionRes(Question question);


    AdminQuestionRes questionToAdminQuestionRes(Question question);

}
