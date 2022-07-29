import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Dive } from 'src/app/models/dive.model';
import { DiveBook } from 'src/app/models/diveBook.model';
import { DiveFormDialogComponent } from '../dive-form-dialog/dive-form-dialog.component';

export interface DialogData {
  dive: Dive;
  clubName: string;
  index: number;
  diveBook: DiveBook;
  dives: Dive[];
}

@Component({
  selector: 'app-dive-info-dialog',
  templateUrl: './dive-info-dialog.component.html',
  styleUrls: ['./dive-info-dialog.component.css']
})
export class DiveInfoDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<DiveInfoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    public dialog: MatDialog) {}

  onNoClick(): void {
    this.dialogRef.close(this.data.dives);
  }

  ngOnInit(): void {
    console.log(this.data.dive)
  }

  openModifyDiveDialog(): void {
    const dialogRef = this.dialog.open(DiveFormDialogComponent, {
      width: '70%',
      data: {dives: this.data.dives, diveBook: this.data.diveBook, dive: this.data.dive}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result !== undefined){
        this.data.dive = result;
        this.data.dives[this.data.index] = this.data.dive;
      }
    });
  }

}
