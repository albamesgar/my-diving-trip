import { Component, OnInit } from '@angular/core';
import { Passport } from 'src/app/models/passport.model';
import { Titulation } from 'src/app/models/titulation.model';
import { EdgeService } from 'src/app/services/edge.service';
import {MatDialog} from '@angular/material/dialog';
import { TitulationFormDialogComponent } from '../titulation-form-dialog/titulation-form-dialog.component';

@Component({
  selector: 'app-passport',
  templateUrl: './passport.component.html',
  styleUrls: ['./passport.component.css']
})
export class PassportComponent implements OnInit {

  passport: Passport;
  titulations: Titulation[];

  constructor(
    private edgeService: EdgeService,
    public dialog: MatDialog
  ){
    this.passport = new Passport(null,0,[]);
    this.titulations=[];
  }

  ngOnInit(): void {
    this.edgeService.getPassport(JSON.parse(localStorage.getItem("currentUser") as string).id).subscribe(
      (passport) => {
        this.passport = passport;
        this.titulations = passport.titulations;
      }
    );
  }

  openNewTitleDialog(): void {
    const dialogRef = this.dialog.open(TitulationFormDialogComponent, {
      width: '60%',
      data: {titulations: this.titulations, passport: this.passport}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}
