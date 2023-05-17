package com.exam.examserver.controller;

import com.exam.examserver.model.exam.Category;
import com.exam.examserver.model.exam.Quiz;
import com.exam.examserver.service.QuizService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("*")
public class QuizController {

    @Autowired
    private QuizService quizService;

    // add quiz

    @PostMapping("/")
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz) {
        Quiz quiz1 = this.quizService.addQuiz(quiz);
        return ResponseEntity.ok(quiz1);
    }

    // get all quizzes
    @GetMapping("/")
    public ResponseEntity<?> quizzes() {
        Set<Quiz> quizzes = this.quizService.getQuizzes();
        return ResponseEntity.ok(quizzes);
    }

    // get quiz by id

    @GetMapping("/{quizId}")
    public Quiz getQuiz(@PathVariable("quizId") Long quizId) {
        return this.quizService.getQuiz(quizId);
    }

    // update quiz

    @PutMapping("/")
    public ResponseEntity<Quiz> update(@RequestBody Quiz quiz) {
        return ResponseEntity.ok(this.quizService.updateQuiz(quiz));
    }

    // delete quiz

    @DeleteMapping("/{quizId}")
    public void delete(@PathVariable("quizId") Long quizId) {
        this.quizService.deleteQuiz(quizId);
    }

    @GetMapping("/category/{cid}")
    public ResponseEntity<?> getQuizzesOfCategory(@PathVariable("cid") Long cid) {
        Category category = new Category();
        category.setCid(cid);
        Set<Quiz> quizzesOfCategory = this.quizService.getQuizzesOfCategory(category);
        return ResponseEntity.ok(quizzesOfCategory);
    }

    // get active quizzes

    @GetMapping("/active")
    public List<Quiz> getActiveQuizzes() {
        return this.quizService.getActiveQuizzes();
    }

    // get active quizzes of category
    @GetMapping("/category/active/{cid}")
    public List<Quiz> getActiveQuizzesOfCategory(@PathVariable("cid") Long cid) {
        Category c = new Category();
        c.setCid(cid);
        return this.quizService.getActiveQuizzesOfCategory(c);
    }



}
