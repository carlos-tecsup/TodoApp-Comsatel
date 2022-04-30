import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, NgForm, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Task } from '../models/task.model';
import { TodoService } from '../services/todo.service';

@Component({
  selector: 'app-todo-edit-dialog',
  templateUrl: './todo-edit-dialog.component.html',
  styleUrls: ['./todo-edit-dialog.component.scss']
})
export class TodoEditDialogComponent implements OnInit {

  constructor(private fb: FormBuilder, private todoService: TodoService,
    public dialogRef: MatDialogRef<TodoEditDialogComponent>, @Inject(MAT_DIALOG_DATA) public task: Task) { }

  public updateForm = this.fb.group({
    description:['',Validators.required],
    id:['',Validators.required],
  })

  ngOnInit(): void {
  }

  close() {
    this.dialogRef.close()
  }
  
  update() {
    const {description} = this.updateForm.value;
    const {id} = this.updateForm.value;

    this.todoService.updateTask(id,description)
    .subscribe(
      () => {
        this.dialogRef.close()
      },
      () => {
      }
    );
  }



}
