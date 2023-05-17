import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { QuestionService } from 'src/app/services/question.service';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-update-question',
  templateUrl: './update-question.component.html',
  styleUrls: ['./update-question.component.css']
})
export class UpdateQuestionComponent {

  public Editor = ClassicEditor;

  constructor(private _route:ActivatedRoute,private _question:QuestionService) {}

  quesId:any;
  question:any;

  ngOnInit(): void {

    this.quesId = this._route.snapshot.params['quesId'];
    this._question.getQuestion(this.quesId).subscribe(
      (data:any) => {
        this.question = data;
        console.log(this.question);
      },
      (error) => {
        console.log(error);
        Swal.fire("Error !!","Error in getting the question","error");
      }
    )

  }

  updateQuestion() {

    if(this.question.content.trim() == '' || this.question.content == null) {
      console.log("Content cannot be empty");
      Swal.fire("Error !!","Enter Content","error");
      return;
    }
    if(this.question.option1.trim() == '' || this.question.option1 == null) {
      console.log("Option1 cannot be empty");
      Swal.fire("Error !!","Enter Option1","error");
      return;
    }
    if(this.question.option2.trim() == '' || this.question.option2 == null) {
      console.log("Option2 cannot be empty");
      Swal.fire("Error !!","Enter Option2","error");
      return;
    }
    if(this.question.answer.trim() == '' || this.question.answer == null) {
      console.log("Answer cannot be empty");
      Swal.fire("Error !!","Enter Answer","error");
      return;
    }

    // update question

    this._question.updateQuestion(this.question).subscribe(
      (data:any) => {
        Swal.fire("Success !!","Question Updated Successfully","success");
        this.question.content = '';
        this.question.option1 = '';
        this.question.option2 = '';
        this.question.option3 = '';
        this.question.option4 = '';
        this.question.answer = '';
      },
      (error) => {
        console.log(error);
        Swal.fire("Error !!","Error in updateing the question. Try Again","error");
      }
    )

  }


}
