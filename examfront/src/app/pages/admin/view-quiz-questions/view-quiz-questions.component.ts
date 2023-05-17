import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { QuestionService } from 'src/app/services/question.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-quiz-questions',
  templateUrl: './view-quiz-questions.component.html',
  styleUrls: ['./view-quiz-questions.component.css']
})
export class ViewQuizQuestionsComponent {

  qid:any;
  title:any;
  questions:any;

  constructor(private _route:ActivatedRoute,private _question:QuestionService) {}

  ngOnInit(): void {

    this.qid = this._route.snapshot.params['qid'];
    this.title = this._route.snapshot.params['title'];
    this._question.getQuestionsOfQuiz(this.qid).subscribe(
      (data:any) => {
        this.questions = data;
        console.log(this.questions);
      },
      (error) => {
        console.log(error);
        Swal.fire("Error !!","Error in getting questions","error");
      }
    )

  }

  deleteQuestion(quesId:any) {
    
    Swal.fire(
    {
      icon:'info',
      showCancelButton:true,
      confirmButtonText:'Delete',
      title:'Are you sure you want to delete this question',
    }
    ).then(
        (result) => {
          if (result.isConfirmed) {
            // confirm delete

            this._question.deleteQuestion(quesId).subscribe(
              (data:any) => {
                this.questions = this.questions.filter((q : any) => (q.quesId) != quesId);
                Swal.fire("Success !!","Question deleted Successfully","success");
              },
              (error) => {
                console.log(error);
                Swal.fire("Error !!","Error in deleting the question.Try Again !!","error");
              }
            )

          }
        }
      )
    }

}
