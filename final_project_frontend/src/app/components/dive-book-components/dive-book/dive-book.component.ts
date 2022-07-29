import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Dive } from 'src/app/models/dive.model';
import { DiveBook } from 'src/app/models/diveBook.model';
import { EdgeService } from 'src/app/services/edge.service';
import { DiveFormDialogComponent } from '../dive-form-dialog/dive-form-dialog.component';

@Component({
  selector: 'app-dive-book',
  templateUrl: './dive-book.component.html',
  styleUrls: ['./dive-book.component.css']
})
export class DiveBookComponent implements OnInit {

  dives: Dive[];
  diveBook: DiveBook;

  constructor(
    private edgeService: EdgeService,
    public dialog: MatDialog
  ) {
    this.dives=[];
    this.diveBook= new DiveBook(null,-1,[]);
   }

  ngOnInit(): void {
    this.edgeService.getDiveBook(JSON.parse(localStorage.getItem("currentUser") as string).id).subscribe(
      (diveBook) => {
        this.diveBook = diveBook;
        this.dives = diveBook.dives;
      }
    );
  }

  openNewDiveDialog(): void {
    const dialogRef = this.dialog.open(DiveFormDialogComponent, {
      width: '70%',
      data: {dives: this.dives, diveBook: this.diveBook, dive: null}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result !== undefined){
        this.dives = result;
      }
    });
  }
}
