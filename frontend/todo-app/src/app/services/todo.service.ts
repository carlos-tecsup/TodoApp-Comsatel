import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from 'src/environments/environment';
import { Task } from '../models/task.model';
import { HttpClient } from '@angular/common/http';
import { map,catchError, tap } from 'rxjs/operators';
import { of, Subject, throwError } from 'rxjs';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class TodoService {
   _baseUrl:string = environment.baseUrl
   _refresh$ = new Subject<void>();
  
  constructor(private http: HttpClient) { }

  get refresh$(){
    return this._refresh$;
  } 
  
  getTasks(): Observable<Task[] > {
    return this.http.get<Task[]>(this._baseUrl + '/tasks').pipe(
      catchError(e => {
        Swal.fire({
          icon: 'error',
          title: 'Servidor caido, por favor espere',
          showConfirmButton: false,
          timer: 10500
        })
        setTimeout(
          function(){ 
          location.reload(); 
          }, 10500);
      return throwError(e);
      }));
  }

  createTask(description:string){
    const url =`${this._baseUrl}/tasks`;
    const body={description} 

    return this.http.post(url,body)
      .pipe(
        tap(() => {
          this._refresh$.next();
        }),
        map(resp=>resp),
        catchError(err=>of(false)));
  }
  
  updateTask(id:number, description:string){
    const url =`${this._baseUrl}/tasks/${id}`;
    const body = {description} ;

    return this.http.put(url,body)
      .pipe(
        tap(() => {
          this._refresh$.next();
        }),
        map(resp=>resp),
        catchError(err=>of(false)));
  }

  deleteTask( id:number ) {
    const url =`${this._baseUrl}/tasks/${id}`;
    return this.http.delete(url)
    .pipe(
      tap(() => {
        this._refresh$.next();
      }),
      map(resp=>resp),
      catchError(err=>of(false)));
  }

  updateStateTask(id:number, completed:boolean){
    const url =`${this._baseUrl}/tasks/state/${id}`;
    const body = {completed} ;

    return this.http.put(url,body)
      .pipe(
        tap(() => {
          this._refresh$.next();
        }),
        map(resp=>resp),
        catchError(err=>of(false)));
  }
}