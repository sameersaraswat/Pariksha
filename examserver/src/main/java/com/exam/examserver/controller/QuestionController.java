package com.exam.examserver.controller;

import com.exam.examserver.model.exam.Question;
import com.exam.examserver.model.exam.Quiz;
import com.exam.examserver.service.QuestionService;
import com.exam.examserver.service.QuizService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.net.http.*;
import java.net.URI;


@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    // add questions

    @PostMapping("/")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(this.questionService.addQuestion(question));
    }

    // get all questions

    @GetMapping("/")
    public ResponseEntity<?> getQuestions() {
        return ResponseEntity.ok(this.questionService.getQuestions());
    }

    // get single questions

    @GetMapping("/{ques_id}")
    public Question getQuestionById(@PathVariable("ques_id") Long ques_id) {
        return this.questionService.getQuestion(ques_id);
    }

    // update question

    @PutMapping("/")
    public ResponseEntity<Question> update(@RequestBody Question question) {
        return ResponseEntity.ok(this.questionService.updateQuestion(question));
    }

    // get questions of quiz

    @GetMapping("/quiz/{quiz_id}")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("quiz_id") Long quiz_id) {
//        Quiz quiz = new Quiz();
//        quiz.setQid(quiz_id);
//        return ResponseEntity.ok(this.questionService.getQuestionOfQuiz(quiz));

        Quiz quiz = this.quizService.getQuiz(quiz_id);
        Set<Question> questions = quiz.getQuestions();
        List<Question> list = new ArrayList(questions);
        if (list.size() > Integer.parseInt(quiz.getNumberOfQuestions())) {
            list = list.subList(0,Integer.parseInt(quiz.getNumberOfQuestions() + 1));
        }
        Collections.shuffle(list);
        list.forEach(q -> {
            q.setAnswer("");
        });
        return ResponseEntity.ok(list);

    }

    @GetMapping("/quiz/all/{quiz_id}")
    public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("quiz_id") Long quiz_id) {
        Quiz quiz = new Quiz();
        quiz.setQid(quiz_id);
        return ResponseEntity.ok(this.questionService.getQuestionOfQuiz(quiz));

    }

    // delete question

    @DeleteMapping("/{ques_id}")
    public void delete(@PathVariable("ques_id") Long ques_id) {
        this.questionService.deleteQuestion(ques_id);
    }


    // evaluate questions
    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions) {
        System.out.println(questions);

        double marksGot = 0;
        Integer correctAnswers = 0;
        Integer attempted = 0;

        double markOfSingleQuestion = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / questions.size();

        for (Question q : questions) {

            Question question = this.questionService.get(q.getQuesId());
            if (question.getAnswer().equals(q.getGivenAnswer())) {
                // correct

                correctAnswers++;

            }
            if (q.getGivenAnswer() != null)
                attempted++;


        }
        marksGot = correctAnswers * markOfSingleQuestion;
        Map<String, Object> map = Map.of("marksGot",marksGot,"correctAnswers",correctAnswers,"attempted",attempted);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/random")
    public ResponseEntity<?> random() throws IOException, InterruptedException {
        System.out.println("in random");
        String url = "https://jservice.io/api/random";

        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();

        var client = HttpClient.newBuilder().build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());

        System.out.println(response.body());

        return ResponseEntity.ok(response.body());
    }
}
