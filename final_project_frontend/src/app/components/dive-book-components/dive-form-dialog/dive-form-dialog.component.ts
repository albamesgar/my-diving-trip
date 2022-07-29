import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Club } from 'src/app/models/club.model';
import { Dive } from 'src/app/models/dive.model';
import { DiveBook } from 'src/app/models/diveBook.model';
import { EdgeService } from 'src/app/services/edge.service';
import { CustomValidators } from 'src/app/utils/custom.validations';
import { MY_DATE_FORMATS } from 'src/app/utils/my-date-formats';

export interface DialogData {
  dives: Dive[];
  diveBook: DiveBook;
  dive: Dive | null;
}

@Component({
  selector: 'app-dive-form-dialog',
  templateUrl: './dive-form-dialog.component.html',
  styleUrls: ['./dive-form-dialog.component.css'],
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS }
  ]
})
export class DiveFormDialogComponent implements OnInit {
  
  diveForm: FormGroup;
  dateInput: FormControl;
  locationInput: FormControl;
  maxDepthInput: FormControl;
  minutesInInput: FormControl;
  partnerNameInput: FormControl;
  partnerTitulationInput: FormControl;
  airEnteringInput: FormControl;
  airOutgoingInput: FormControl;
  temperatureInput: FormControl;
  visibilityInput: FormControl;
  bottleCapacityInput: FormControl;
  airTypeInput: FormControl;
  oxygenProportionInput: FormControl;
  clubNameInput: FormControl;
  observationsInput: FormControl;
  pictureInput: FormControl;
  ratingInput: FormControl;

  dive: Dive;
  
  clubs: Club [];
  filteredOptions!: Observable<Club[]>;
  bottles: string[];
  gases: string[];
  title: string;
  currentClub: Club;

  constructor(
    private edgeService: EdgeService,
    public dialogRef: MatDialogRef<DiveFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) {
    this.dive = new Dive(null,new Date(''),'',0,0,null,null,null,null,null,null,null,'','',null,null,null,
    "https://tse3.mm.bing.net/th?id=OIP.L1gVs6jIEPyVGI6N3R170gHaFE&pid=Api", false, this.data.diveBook);
    this.clubs =[];
    this.bottles = ['12L','15L','18L','Other']
    this.gases = ['Air', 'NITROX', 'Other']
    this.currentClub = new Club(null,'','',0,'',0,'',0,'',0);

    this.title = "Add new Dive";
    if (this.data.dive !== null){
      this.title = "Modify Dive";
    }

    this.dateInput = new FormControl('', [Validators.required]);
    this.locationInput = new FormControl('', [Validators.required]);
    this.maxDepthInput = new FormControl('', [Validators.required]);
    this.minutesInInput = new FormControl('', [Validators.required, Validators.min(0)]);
    this.partnerNameInput = new FormControl('', [Validators.required]);
    this.partnerTitulationInput = new FormControl('', [Validators.required]);
    this.airEnteringInput = new FormControl('',Validators.min(0));
    this.airOutgoingInput = new FormControl('',Validators.min(0));
    this.temperatureInput = new FormControl('');
    this.visibilityInput = new FormControl('',Validators.min(0));
    this.bottleCapacityInput = new FormControl('');
    this.airTypeInput = new FormControl('');
    this.oxygenProportionInput = new FormControl('',Validators.min(0));
    this.clubNameInput = new FormControl('');
    // this.clubNameInput = new FormControl('',[CustomValidators.ClubNoExist(this.clubs)]);
    this.observationsInput = new FormControl('');
    this.pictureInput = new FormControl('');
    this.ratingInput = new FormControl('');

    if (this.data.dive !== null){
      this.dateInput = new FormControl(this.data.dive.date, [Validators.required]);
      this.locationInput = new FormControl(this.data.dive.location, [Validators.required]);
      this.maxDepthInput = new FormControl(this.data.dive.maxDepth, [Validators.required]);
      this.minutesInInput = new FormControl(this.data.dive.minutesIn, [Validators.required,Validators.min(0)]);
      this.partnerNameInput = new FormControl(this.data.dive.partnerName, [Validators.required]);
      this.partnerTitulationInput = new FormControl(this.data.dive.partnerTitulation, [Validators.required]);
      this.airEnteringInput = new FormControl(this.data.dive.airEntering,Validators.min(0));
      this.airOutgoingInput = new FormControl(this.data.dive.airOutgoing,Validators.min(0));
      this.temperatureInput = new FormControl(this.data.dive.temperature);
      this.visibilityInput = new FormControl(this.data.dive.visibility,Validators.min(0));
      this.bottleCapacityInput = new FormControl(this.data.dive.bottleCapacity);
      this.airTypeInput = new FormControl(this.data.dive.airType);
      this.oxygenProportionInput = new FormControl(this.data.dive.oxygenProportion,Validators.min(0));
      if (this.data.dive.clubId !== null && this.data.dive.clubId !== undefined){
        this.clubNameInput = new FormControl(this.currentClub);
      } else {
        this.clubNameInput = new FormControl('');
      }
      // this.clubNameInput = new FormControl('',[CustomValidators.ClubNoExist(this.clubs)]);
      this.observationsInput = new FormControl(this.data.dive.observations);
      this.pictureInput = new FormControl(this.data.dive.picture);
      this.ratingInput = new FormControl(this.data.dive.rating);
    }

    this.diveForm = new FormGroup({
      date: this.dateInput,
      location: this.locationInput,
      maxDepth: this.maxDepthInput,
      minutesIn: this.minutesInInput,
      partnerName: this.partnerNameInput,
      partnerTitulation: this.partnerTitulationInput,
      airEntering: this.airEnteringInput,
      airOutgoing: this.airOutgoingInput,
      temperature: this.temperatureInput,
      visibility: this.visibilityInput,
      bottleCapacity: this.bottleCapacityInput,
      airType: this.airTypeInput,
      oxygenProportion: this.oxygenProportionInput,
      clubName: this.clubNameInput,
      observations: this.observationsInput,
      picture: this.pictureInput,
      rating: this.ratingInput
    })
   }

  ngOnInit(): void {
    if(this.data.dive?.clubId !== null && this.data.dive?.clubId !== undefined){
      this.edgeService.getClubById(this.data.dive.clubId).subscribe(
        (club) => {
          this.currentClub = club;
        }
      )
    }
    this.edgeService.getAllClubs().subscribe(
      (clubs) => {
        this.clubs = clubs;
      }
    );
    this.filteredOptions = this.clubNameInput.valueChanges.pipe(
      startWith(''),
      map(value => {
        const name = typeof value === 'string' ? value : value?.name;
        return name ? this._filter(name as string) : this.clubs.slice();
      }),
    );
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(){
    if (this.diveForm.value.clubName === null){
      this.diveForm.value.clubName = new Club(null,'','',0,'',0,'',0,'',0);
    }
    if (this.diveForm.value.picture === null || this.diveForm.value.picture === ''){
      this.diveForm.value.picture = "https://tse3.mm.bing.net/th?id=OIP.L1gVs6jIEPyVGI6N3R170gHaFE&pid=Api";
    }
    this.dive = new Dive (null, this.diveForm.value.date, this.diveForm.value.location, this.diveForm.value.maxDepth,
      this.diveForm.value.minutesIn, this.diveForm.value.airEntering, this.diveForm.value. airOutgoing, this.diveForm.value. temperature,
      this.diveForm.value.visibility, this.diveForm.value.bottleCapacity, this.diveForm.value.airType, 
      this.diveForm.value.oxygenProportion, this.diveForm.value.partnerName, this.diveForm.value.partnerTitulation,
      this.diveForm.value.clubName.id, this.diveForm.value.rating, this.diveForm.value.observations,
      this.diveForm.value.picture, false, this.data.diveBook);

    this.edgeService.addDiveToDiveBook(JSON.parse(localStorage.getItem("currentUser") as string).id, this.dive).subscribe(
      (dive) => {
        this.data.dives.push(dive)
        this.dive = new Dive(null,new Date(''),'',0,0,null,null,null,null,null,null,null,'','',null,null,null,
          "https://tse3.mm.bing.net/th?id=OIP.L1gVs6jIEPyVGI6N3R170gHaFE&pid=Api", false, this.data.diveBook);
        this.diveForm.reset();
      }
    )
  }

  modify(){
    if (this.data.dive !== null){
      if (this.data.dive.id !== null){
        if (this.diveForm.value.clubName === null){
          this.diveForm.value.clubName = new Club(null,'','',0,'',0,'',0,'',0);
        }
        if (this.data.dive.clubValidation === undefined){
          this.data.dive.clubValidation = false;
        }
        if (this.diveForm.value.picture === null || this.diveForm.value.picture === ''){
          this.diveForm.value.picture = "https://tse3.mm.bing.net/th?id=OIP.L1gVs6jIEPyVGI6N3R170gHaFE&pid=Api";
        }
        this.dive = new Dive (null, this.diveForm.value.date, this.diveForm.value.location, this.diveForm.value.maxDepth,
          this.diveForm.value.minutesIn, this.diveForm.value.airEntering, this.diveForm.value. airOutgoing, this.diveForm.value. temperature,
          this.diveForm.value.visibility, this.diveForm.value.bottleCapacity, this.diveForm.value.airType, 
          this.diveForm.value.oxygenProportion, this.diveForm.value.partnerName, this.diveForm.value.partnerTitulation,
          this.diveForm.value.clubName.id, this.diveForm.value.rating, this.diveForm.value.observations,
          this.diveForm.value.picture, this.data.dive.clubValidation, this.data.diveBook);

          console.log(this.data.dive)

        this.edgeService.modifyDiveOfDiveBook(this.data.dive.id, this.dive).subscribe(
          () => {
            this.dialogRef.close(this.dive);
            this.diveForm.reset();
          }
        )
      }
    }
  }

  displayFn(club: Club): string {
    return club && club.name ? club.name : '';
  }

  private _filter(name: string): Club[] {
    const filterValue = name.toLowerCase();

    return this.clubs.filter(option => option.name.toLowerCase().includes(filterValue));
  }

}

