import { Component, OnInit } from '@angular/core';
import { Club } from 'src/app/models/club.model';
import { EdgeService } from 'src/app/services/edge.service';

@Component({
  selector: 'app-clubs-page',
  templateUrl: './clubs-page.component.html',
  styleUrls: ['./clubs-page.component.css']
})
export class ClubsPageComponent implements OnInit {
  clubs: Club[]

  constructor(
    private edgeService: EdgeService
  ) {
    this.clubs = []
   }

  ngOnInit(): void {
    this.edgeService.getAllClubs().subscribe(
      (clubs) => {
        this.clubs = clubs;
      }
    );
  }

}
