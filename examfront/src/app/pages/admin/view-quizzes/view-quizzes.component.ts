import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { QuizService } from 'src/app/services/quiz.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-quizzes',
  templateUrl: './view-quizzes.component.html',
  styleUrls: ['./view-quizzes.component.css']
})
export class ViewQuizzesComponent {

  constructor(private _quiz:QuizService) {}

  quizzes = [
    {
      qid:'',
      title:"",
      description:"",
      maxMarks:"",
      numberOfQuestions:"",
      active:'',
      category:{
        title:'',
      }
    }
  ]

  ngOnInit(): void {
    this._quiz.quizzes().subscribe(
      (data:any)=> {
        this.quizzes = data;
        console.log(this.quizzes);

      },
      (error) => {
        console.log(error);
        Swal.fire("Error !","Error in loading data","error");
      }
    )
  }

  // delete quiz

  deleteQuiz(qid:any) {

    Swal.fire({
      icon:'info',
      title:'Are you sure you want to delete?',
      confirmButtonText:'Yes, I am sure',
      showCancelButton:true
    }).then(
      (result) => {
        if (result.isConfirmed) {
          // delete
          this._quiz.deleteQuiz(qid).subscribe(
            (data:any) => {
              this.quizzes = this.quizzes.filter((quiz) => quiz.qid != qid);
              Swal.fire("Success !!","Quiz Deleted Successfully","success");
            },
            (error) => {
              console.log(error);
              Swal.fire("Error !!","Error in deleting quiz","error");
            }
          )
        }
      }
    )
  }

}
