import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { QuestionService } from 'src/app/services/question.service';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-question',
  templateUrl: './add-question.component.html',
  styleUrls: ['./add-question.component.css']
})
export class AddQuestionComponent {

  public Editor = ClassicEditor;


  qid:any;
  title:any;
  question = {
    quiz:{qid:'',title:''},
    content:'',
    option1:'',
    option2:'',
    option3:'',
    option4:'',
    answer:'',
  }

  constructor(private _route:ActivatedRoute,private _question:QuestionService) {}

  ngOnInit(): void {

    this.qid = this._route.snapshot.params['qid'];
    this.title = this._route.snapshot.params['title'];
    this.question['quiz']['qid'] = this.qid;
    this.question['quiz']['title'] = this.title;
     



  }

  formSubmit() {
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

    // submit form

    this._question.addQuestion(this.question).subscribe(
      (data:any) => {
        console.log(data);
        Swal.fire("Success !!","Question Added Successfully","success");
        this.question.content = '';
        this.question.option1 = '';
        this.question.option2 = '';
        this.question.option3 = '';
        this.question.option4 = '';
        this.question.answer = '';
        
      },
      (error) => {
        console.log(error);
        Swal.fire("Error !!","Error in adding question","error");
      }
    )
  }

}
