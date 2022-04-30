import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Subscription } from 'rxjs/internal/Subscription';
import { Task } from 'src/app/models/task.model';
import { TodoService } from 'src/app/services/todo.service';
import { TodoEditDialogComponent } from 'src/app/todo-edit-dialog/todo-edit-dialog.component';
import { MatDialog } from '@angular/material/dialog';

import swal from 'sweetalert2';
import { Observable } from 'rxjs/internal/Observable';

@Component({
  selector: 'app-todos',
  templateUrl: './todos.component.html',
  styleUrls: ['./todos.component.scss']
})
export class TodosComponent implements OnInit {
  tasks: Observable<Task[]>;
  showValidationErrors: boolean
  suscription :Subscription;

  constructor(private fb: FormBuilder, private todoService: TodoService, private dialog: MatDialog) {
   }

  public todoForm = this.fb.group({
    description:['',Validators.required],
  })

  ngOnInit(): void {
     this.getTasks();
     this.suscription = this.todoService.refresh$.subscribe(()=>{
            this.getTasks();
     });
  }

  toggleCompleted(task: Task) {
    task.completed = !task.completed;
    this.todoService.updateStateTask(task.id,task.completed).subscribe(
      () => {
    
      }
    );
  }
  get description() {
    return this.todoForm.get('description');
  }
  create() {
    if (this.todoForm.invalid) return this.showValidationErrors = true

    const {description} = this.todoForm.value;
    this.todoService.createTask(description)
    .subscribe(
      () => {
        this.todoForm.reset();
        swal.fire({
          icon: 'success',
          title: 'Tarea creada',
        })
      }
    );
  }

  getTasks(){
    this.tasks = this.todoService.getTasks();
  }

  editTask(task: Task) {
    this.dialog.open(TodoEditDialogComponent, {
      width: '700px',
      data: task
    });
  }

  deleteTask(task: Task) {
    swal.fire({
      title: 'Eliminar tarea',
      text: task.description,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      cancelButtonText:'Cancelar',
      confirmButtonText: 'Eliminar'
    }).then((result) => {
      if (result.value) {
        this.todoService.deleteTask( task.id).subscribe(
          ()=>{

          }
        )
      }
    })
  }
}
