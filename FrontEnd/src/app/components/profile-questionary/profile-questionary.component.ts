import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {animate, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-profile-questionary',
  templateUrl: './profile-questionary.component.html',
  styleUrls: ['./profile-questionary.component.css'],
  animations: [
    trigger('formAnimation', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(-50px)' }),
        animate('500ms', style({ opacity: 1, transform: 'translateY(0)' }))
      ])
    ])
  ]
})
export class ProfileQuestionaryComponent implements OnInit {

   // Установка значения по умолчанию
  constructor(private formBuilder: FormBuilder) { }

  documentForm = this.formBuilder.group({
      age: [''],
      destination: ["1",Validators.required],
      budget: [''],
      duration: [''],
      startDate: [''],
      endDate: ['']
  });

  ngOnInit(): void {
  }

  onSubmit() {

  }

}
