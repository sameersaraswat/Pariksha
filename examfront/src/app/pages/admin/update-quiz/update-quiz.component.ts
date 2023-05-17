import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { CategoryService } from 'src/app/services/category.service';
import { QuizService } from 'src/app/services/quiz.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-update-quiz',
  templateUrl: './update-quiz.component.html',
  styleUrls: ['./update-quiz.component.css']
})
export class UpdateQuizComponent {

  constructor(private _route:ActivatedRoute,private _quiz:QuizService,private _cat:CategoryService,private _snack:MatSnackBar,private _router:Router) {}

  qid = 0;
  quiz:any;
  categories:any;

  ngOnInit(): void {

    this.qid = this._route.snapshot.params['qid']
    // alert(this.qid)

    this._quiz.getQuiz(this.qid).subscribe(
      (data:any) => {
        this.quiz = data;
        console.log(this.quiz);

      },
      (error) => {
        console.log(error);
        Swal.fire("Error !!","Error in fetching the data","error");
      }
    );

    this._cat.categories().subscribe(
      (data:any) => {
        this.categories = data;
        console.log(this.categories);
      },
      (error) => {
        console.log(error);
        Swal.fire("Error !!","Error in fetching categories","error");
      }
    );

  }

  // update form submit

  public updateData() {

    if (this.quiz.title.trim() == '' || this.quiz.title == null) {
      this._snack.open("Title required !!","",{
        duration:3000,
      })
      return;
    }

    this._quiz.updateQuiz(this.quiz).subscribe(
      (data:any) => {
        console.log(data);
        Swal.fire("Success !!","Quiz Updated Successfully","success").then((e) => {
          this._router.navigate(['admin/quizzes']);
        })
      },
      (error) => {
        console.log(error);
        Swal.fire("Error !!","Error in updating the quiz","error");
      }
    )

  }

}
