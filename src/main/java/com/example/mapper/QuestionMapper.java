package com.example.mapper;

import com.example.dto.admin.AdminQuestionRes;
import com.example.entity.Question;
import java.util.List;

/**
 * @author BAO 7/20/2023
 */
public interface QuestionMapper {



    AdminQuestionRes questionToAdminQuestionRes(Question question);
    List<AdminQuestionRes> questionsToAdminQuestionsRes(List<Question> questions);

}
