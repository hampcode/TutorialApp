import { TutorialService } from './../../services/tutorial.service';
import { Tutorial } from './../../models/tutorial.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-tutorials-list',
  templateUrl: './tutorials-list.component.html',
  styleUrls: ['./tutorials-list.component.css'],
})
export class TutorialsListComponent implements OnInit {
  tutorials: Tutorial[] = [];
  currentTutorial: Tutorial = {};
  currentIndex = -1;
  title = '';

  page = 1;
  count = 0;
  pageSize = 3;
  pageSizes = [3, 6, 9];
  content?: string;
  constructor(private tutorialService: TutorialService) {}

  ngOnInit(): void {
    this.retrieveTutorials();
  }

  getRequestParams(searchTitle: string, page: number, pageSize: number): any {
    let params: any = {};
    if (searchTitle) {
      params[`title`] = searchTitle;
    }
    if (page) {
      params[`page`] = page - 1;
    }
    if (pageSize) {
      params[`size`] = pageSize;
    }
    return params;
  }

  retrieveTutorials(): void {
    const params = this.getRequestParams(this.title, this.page, this.pageSize);

    this.tutorialService.getAll(params).subscribe(
      (response) => {
        const { tutorials, totalItems } = response;
        this.tutorials = tutorials;
        this.count = totalItems;
      },
      (error) => {
        this.content = error.message;
      }
    );
  }

  handlePageChange(event: number): void {
    this.page = event;
    this.retrieveTutorials();
  }
  handlePageSizeChange(event: any): void {
    this.pageSize = event.target.value;
    this.page = 1;
    this.retrieveTutorials();
  }

  refreshList(): void {
    this.retrieveTutorials();
    this.currentTutorial = {};
    this.currentIndex = -1;
  }
  setActiveTutorial(tutorial: Tutorial, index: number): void {
    this.currentTutorial = tutorial;
    this.currentIndex = index;
  }
  removeAllTutorials(): void {
    this.tutorialService.deleteAll().subscribe({
      next: (res) => {
        console.log(res);
        this.refreshList();
      },
      error: (e) => (this.content = e.error.message),
    });
  }
  searchTitle(): void {
    this.page = 1;
    this.retrieveTutorials();
  }
}
