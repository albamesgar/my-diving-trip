import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { EdgeService } from 'src/app/services/edge.service';

@Component({
  selector: 'app-delete-user-dialog',
  templateUrl: './delete-user-dialog.component.html',
  styleUrls: ['./delete-user-dialog.component.css']
})
export class DeleteUserDialogComponent implements OnInit {

  constructor(
    private edgeService: EdgeService,
    private router: Router,
    public dialogRef: MatDialogRef<DeleteUserDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public user: User
  ) { }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  deleteUser(){
    this.user = JSON.parse(localStorage.getItem("currentUser") as string);
    if (this.user.id !== null){
      this.edgeService.deleteUser(this.user.id).subscribe( () => {
        localStorage.removeItem('currentUser');
        this.router.navigate(['/login']);
      });
    }
  }

}
