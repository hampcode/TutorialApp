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

  /*getAll(): Observable<Tutorial[]> {
    return this.http.get<Tutorial[]>(this.baseUrl);
  }*/
  getAll(params: any): Observable<any> {
    return this.http.get<any>(this.baseUrl, { params });
  }
  get(id: any): Observable<Tutorial> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }
  create(data: any): Observable<any> {
    return this.http.post(this.baseUrl, data);
  }
  update(id: any, data: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}`, data);
  }
  delete(id: any): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
  deleteAll(): Observable<any> {
    return this.http.delete(this.baseUrl);
  }
  findByTitle(title: any): Observable<Tutorial[]> {
    return this.http.get<Tutorial[]>(`${this.baseUrl}?title=${title}`);
  }
}
