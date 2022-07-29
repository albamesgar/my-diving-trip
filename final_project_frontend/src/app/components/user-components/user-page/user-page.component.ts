import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { ModifyUserDialogComponent } from '../modify-user-dialog/modify-user-dialog.component';
import {MatDialog} from '@angular/material/dialog';
import { DeleteUserDialogComponent } from '../delete-user-dialog/delete-user-dialog.component';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {
  user: User;

  constructor(
    public dialog: MatDialog
  ) {
    this.user = new User(null,'','', '','',new Date(),'','','');
  }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem("currentUser") as string);
  }

  openModifyUserDialog(): void {
    const dialogRef = this.dialog.open(ModifyUserDialogComponent, {
      width: '50%',
      data: this.user
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result !== undefined){
        this.user = result;
      }
      console.log('The dialog was closed');
    });
  }

  openDeleteUserDialog(): void {
    const dialogRef = this.dialog.open(DeleteUserDialogComponent, {
      width: 'auto',
      // height: '200px',
      data: this.user
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
