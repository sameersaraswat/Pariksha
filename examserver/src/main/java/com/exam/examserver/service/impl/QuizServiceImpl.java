package com.exam.examserver.service.impl;

import com.exam.examserver.model.exam.Category;
import com.exam.examserver.model.exam.Quiz;
import com.exam.examserver.repo.QuizRepository;
import com.exam.examserver.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return this.quizRepository.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return this.quizRepository.save(quiz);
    }

    @Override
    public Set<Quiz> getQuizzes() {
        return new HashSet<>(this.quizRepository.findAll());
    }

    @Override
    public Quiz getQuiz(Long quizId) {
        return this.quizRepository.findById(quizId).get();
    }

    @Override
    public void deleteQuiz(Long quizId) {

        Quiz quiz = new Quiz();
        quiz.setQid(quizId);

        this.quizRepository.delete(quiz);
    }

    @Override
    public Set<Quiz> getQuizzesOfCategory(Category category) {
        Set<Quiz> bycategory = this.quizRepository.findBycategory(category);
        return bycategory;
    }

    // get active quizzes
    @Override
    public List<Quiz> getActiveQuizzes() {
        return this.quizRepository.findByActive(true);
    }

    @Override
    public List<Quiz> getActiveQuizzesOfCategory(Category c) {
        return this.quizRepository.findByCategoryAndActive(c,true);
    }



}
