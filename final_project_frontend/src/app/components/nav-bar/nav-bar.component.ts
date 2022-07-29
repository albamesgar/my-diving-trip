import { Component, OnInit } from '@angular/core';
import { EdgeService } from 'src/app/services/edge.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  constructor(
    private edgeService: EdgeService
  ) { }

  ngOnInit(): void {
  }

  logOut(){
    this.edgeService.logout()
    // .subscribe(
    //   () => {
    //     console.log("Log out")
    //   }
    // )
  }

}
