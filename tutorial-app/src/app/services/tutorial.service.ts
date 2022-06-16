import { environment } from './../../environments/environment';
import { Tutorial } from './../models/tutorial.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TutorialService {
  private baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) {}

  getAll(params: any): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}tutorials`, { params });
  }
  get(id: any): Observable<Tutorial> {
    return this.http.get(`${this.baseUrl}tutorials/${id}`);
  }
  create(data: any): Observable<any> {
    return this.http.post(this.baseUrl, data);
  }
  update(id: any, data: any): Observable<any> {
    return this.http.put(`${this.baseUrl}tutorials/${id}`, data);
  }
  delete(id: any): Observable<any> {
    return this.http.delete(`${this.baseUrl}tutorials/${id}`);
  }
  deleteAll(): Observable<any> {
    return this.http.delete(this.baseUrl);
  }
  findByTitle(title: any): Observable<Tutorial[]> {
    return this.http.get<Tutorial[]>(
      `${this.baseUrl}tutorials/?title=${title}`
    );
  }
}
