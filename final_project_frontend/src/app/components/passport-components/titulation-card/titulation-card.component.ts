import { Component, Input, OnInit } from '@angular/core';
import { Passport } from 'src/app/models/passport.model';
import { Titulation } from 'src/app/models/titulation.model';
import { EdgeService } from 'src/app/services/edge.service';

@Component({
  selector: 'app-titulation-card',
  templateUrl: './titulation-card.component.html',
  styleUrls: ['./titulation-card.component.css']
})
export class TitulationCardComponent implements OnInit {

  @Input()
  titulation: Titulation;

  @Input()
  index: number;

  @Input()
  passport: Passport;

  clubName:string;

  constructor(
    private edgeService: EdgeService
  ) {
    this.passport = new Passport(null,0,[]);
    this.titulation = new Titulation(null,'','',new Date(),'',0,this.passport);
    this.index=0;
    this.clubName = '';
   }

  ngOnInit(): void {
    this.edgeService.getClubById(this.titulation.clubId).subscribe(
      (club) => {
        this.clubName = club.name;
      }
    )
  }

}
