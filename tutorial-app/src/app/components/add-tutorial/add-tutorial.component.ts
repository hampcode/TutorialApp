import { TutorialService } from './../../services/tutorial.service';
import { Tutorial } from './../../models/tutorial.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-tutorial',
  templateUrl: './add-tutorial.component.html',
  styleUrls: ['./add-tutorial.component.css'],
})
export class AddTutorialComponent implements OnInit {
  tutorial: Tutorial = {
    title: '',
    description: '',
    published: false,
  };
  submitted = false;
  content?: string;

  constructor(private tutorialService: TutorialService) {}

  ngOnInit(): void {}

  saveTutorial(): void {
    const data = {
      title: this.tutorial.title,
      description: this.tutorial.description,
    };
    this.tutorialService.create(data).subscribe({
      next: (res) => {
        this.submitted = true;
      },
      error: (e) => (this.content = e.error.message),
    });
  }
  newTutorial(): void {
    this.submitted = false;
    this.tutorial = {
      title: '',
      description: '',
      published: false,
    };
  }
}
