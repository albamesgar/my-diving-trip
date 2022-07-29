import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Club } from 'src/app/models/club.model';
import { Passport } from 'src/app/models/passport.model';
import { Titulation } from 'src/app/models/titulation.model';
import { EdgeService } from 'src/app/services/edge.service';
import { CustomValidators } from 'src/app/utils/custom.validations';
import { MY_DATE_FORMATS } from 'src/app/utils/my-date-formats';

export interface DialogData {
  titulations: Titulation[];
  passport: Passport;
}

@Component({
  selector: 'app-titulation-form-dialog',
  templateUrl: './titulation-form-dialog.component.html',
  styleUrls: ['./titulation-form-dialog.component.css'],
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS }
  ]
})
export class TitulationFormDialogComponent implements OnInit {
  titulationForm: FormGroup;
  organizationInput: FormControl;
  titleNameInput: FormControl;
  dateObtainedInput: FormControl;
  instructorNameInput: FormControl;
  clubNameInput: FormControl;
  organizations: string[];

  titulation: Titulation;
  clubs: Club[];
  filteredOptions!: Observable<Club[]>;

  constructor(
    private edgeService: EdgeService,
    public dialogRef: MatDialogRef<TitulationFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) {
    this.organizations = ["FEDAS", "PADI", "CEMAS", "SSI", "Other"];
    this.clubs = [];

    this.organizationInput = new FormControl('', Validators.required);
    this.titleNameInput = new FormControl('', Validators.required);
    this.dateObtainedInput = new FormControl('', Validators.required);
    this.instructorNameInput = new FormControl('', Validators.required);
    this.clubNameInput = new FormControl('', [Validators.required]);
    // this.clubNameInput = new FormControl('', [Validators.required, CustomValidators.ClubNoExist(this.clubs)]);

    this.titulationForm = new FormGroup({
      organization: this.organizationInput,
      titleName: this.titleNameInput,
      dateObtained: this.dateObtainedInput,
      instructorName: this.instructorNameInput,
      clubName: this.clubNameInput
    })

    this.titulation = new Titulation(null,'','',new Date(),'',0, this.data.passport);
   }

  ngOnInit(): void {
    this.edgeService.getAllClubs().subscribe(
      (clubs) => {
        this.clubs = clubs;

        this.organizationInput = new FormControl('', Validators.required);
        this.titleNameInput = new FormControl('', Validators.required);
        this.dateObtainedInput = new FormControl('', Validators.required);
        this.instructorNameInput = new FormControl('', Validators.required);
        this.clubNameInput = new FormControl('', [Validators.required]);
        // this.clubNameInput = new FormControl('', [Validators.required, CustomValidators.ClubNoExist(this.clubs)]);

        this.titulationForm = new FormGroup({
          organization: this.organizationInput,
          titleName: this.titleNameInput,
          dateObtained: this.dateObtainedInput,
          instructorName: this.instructorNameInput,
          clubName: this.clubNameInput
        })
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
    console.log(this.titulationForm.value)
    this.titulation = new Titulation(null, this.titulationForm.value.organization, this.titulationForm.value.titleName,
      this.titulationForm.value.dateObtained, this.titulationForm.value.instructorName, this.titulationForm.value.clubName.id, 
      this.data.passport);
    this.edgeService.addTitulation(JSON.parse(localStorage.getItem("currentUser") as string).id, this.titulation).subscribe(
      (titulation) => {
        this.data.titulations.push(titulation)
        this.titulation = new Titulation(null,'','',new Date(),'',0,this.data.passport);
        this.titulationForm.reset();
      }
    )
  }

  displayFn(club: Club): string {
    return club && club.name ? club.name : '';
  }

  private _filter(name: string): Club[] {
    const filterValue = name.toLowerCase();

    return this.clubs.filter(club => club.name.toLowerCase().includes(filterValue));
  }

}
