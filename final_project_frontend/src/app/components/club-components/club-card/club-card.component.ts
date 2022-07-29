import { Component, Input, OnInit } from '@angular/core';
import { Club } from 'src/app/models/club.model';

@Component({
  selector: 'app-club-card',
  templateUrl: './club-card.component.html',
  styleUrls: ['./club-card.component.css']
})
export class ClubCardComponent implements OnInit {
  @Input()
  club: Club;

  @Input()
  index: number;

  constructor() {
    this.club = new Club(null,'','',0,'',0,'',0,'',0)
    this.index = 0;
   }

  ngOnInit(): void {
  }

}
