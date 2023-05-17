package com.exam.examserver.service;

import java.util.Set;

import com.exam.examserver.model.exam.Question;
import com.exam.examserver.model.exam.Quiz;

public interface QuestionService {

    public Question addQuestion(Question question);

    public Question updateQuestion (Question question);

    public Set<Question> getQuestions();

    public Question getQuestion(Long questionId);

    public Set<Question> getQuestionOfQuiz(Quiz quiz);

    public void deleteQuestion(Long quizId);

    public Question get(Long quesId);

}
