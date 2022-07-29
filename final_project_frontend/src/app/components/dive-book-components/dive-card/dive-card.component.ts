import { Component, Inject, Input, OnInit } from '@angular/core';
import { Dive } from 'src/app/models/dive.model';
import { DiveBook } from 'src/app/models/diveBook.model';
import { EdgeService } from 'src/app/services/edge.service';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { DiveInfoDialogComponent } from '../dive-info-dialog/dive-info-dialog.component';

@Component({
  selector: 'app-dive-card',
  templateUrl: './dive-card.component.html',
  styleUrls: ['./dive-card.component.css']
})
export class DiveCardComponent implements OnInit {
  @Input()
  dive: Dive;

  @Input()
  index: number;

  @Input()
  diveBook: DiveBook;

  @Input()
  dives: Dive[];

  clubName: string;

  constructor(
    private edgeService: EdgeService,
    public dialog: MatDialog
    ) {
    this.diveBook= new DiveBook(null,-1,[]);
    this.dive = new Dive(null,new Date(''),'',0,0,null,null,null,null,null,null,null,'','',null,null,null,
    "https://tse3.mm.bing.net/th?id=OIP.L1gVs6jIEPyVGI6N3R170gHaFE&pid=Api", false, this.diveBook);
    this.index=0;
    this.clubName = ''
    this.dives=[];
   }

  ngOnInit(): void {
    if (this.dive.clubId !== null && this.dive.clubId !== undefined){
    this.edgeService.getClubById(this.dive.clubId).subscribe(
      (club) => {
        this.clubName = club.name;
      }
    )
    }
  }

  openShowDialog(): void {
    const dialogRef = this.dialog.open(DiveInfoDialogComponent, {
      width: '70%',
      data: {dive: this.dive, clubName: this.clubName, index: this.index, diveBook: this.diveBook, dives: this.dives}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}


