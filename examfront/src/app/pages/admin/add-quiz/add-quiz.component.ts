import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CategoryService } from 'src/app/services/category.service';
import { QuizService } from 'src/app/services/quiz.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-quiz',
  templateUrl: './add-quiz.component.html',
  styleUrls: ['./add-quiz.component.css']
})
export class AddQuizComponent {

  constructor(private _cat:CategoryService,private _snack:MatSnackBar,private _quiz:QuizService) {}

  categories = [
    {
      cid:"",
      'title':"",
    }
  ]

  quizData = {
    title:'',
    description:'',
    maxMarks:'',
    numberOfQuestions:'',
    active:true,
    category:{
      cid:'',
    }
  }

  ngOnInit(): void {
    this._cat.categories().subscribe(
      (data:any) => {
        this.categories = data;
      },
      (error) => {
        console.log(error);
        Swal.fire("Error !!","Error in loading data","error");
      }
    )
  }
  // add quiz

  addQuiz() {
    console.log(this.quizData);

    if (this.quizData.title.trim() == '' || this.quizData.title == null) {
      this._snack.open("Title required !!","",{
        duration:3000,
      })
      return;
    }

    // call server

    this._quiz.addQuiz(this.quizData).subscribe(
      (data:any) => {
        Swal.fire("Sucess !!","Quiz Added Successfully","success");
        this.quizData = {
          title:'',
          description:'',
          maxMarks:'',
          numberOfQuestions:'',
          active:true,
          category:{
            cid:'',
          }
        }

      },
      (error) => {
        console.log(error);
        Swal.fire("Error !","Error in sending data",'error');
      }
    )

  }

}
