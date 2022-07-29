import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { User } from 'src/app/models/user.model';
import { EdgeService } from 'src/app/services/edge.service';
import { CustomValidators } from 'src/app/utils/custom.validations';
import { MY_DATE_FORMATS } from 'src/app/utils/my-date-formats';

@Component({
  selector: 'app-modify-user-dialog',
  templateUrl: './modify-user-dialog.component.html',
  styleUrls: ['./modify-user-dialog.component.css'],
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS }
  ]
})
export class ModifyUserDialogComponent implements OnInit {

  registerForm: FormGroup;
  usernameInput: FormControl;
  passwordInput: FormControl;
  passwordConfirmationInput: FormControl;
  firstNameInput: FormControl;
  lastNameInput: FormControl;
  emailInput: FormControl;
  birthDateInput: FormControl;
  profilePictureInput: FormControl;

  existingUsernames: string[];

  constructor(
    private edgeService: EdgeService,
    public dialogRef: MatDialogRef<ModifyUserDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public user: User) 
    {
      this.existingUsernames = [];

      this.usernameInput = new FormControl(this.user.username, [Validators.required, 
        CustomValidators.usernameExist(this.existingUsernames,this.user.username)]);
      this.passwordInput = new FormControl('', [Validators.required]);
      this.passwordConfirmationInput = new FormControl('', [Validators.required]);
      this.firstNameInput = new FormControl(this.user.firstName, [Validators.required]);
      this.lastNameInput = new FormControl(this.user.lastName, [Validators.required]);
      this.emailInput = new FormControl(this.user.email, [Validators.required, Validators.email]);
      this.birthDateInput = new FormControl(this.user.birthDate,[Validators.required]);
      this.profilePictureInput = new FormControl(this.user.profilePicture);

      this.registerForm = new FormGroup({
        username: this.usernameInput,
        password: this.passwordInput,
        passwordConfirmation: this.passwordConfirmationInput,
        firstName: this.firstNameInput,
        lastName: this.lastNameInput,
        email: this.emailInput,
        birthDate: this.birthDateInput,
        profilePicture: this.profilePictureInput
      }, [CustomValidators.passwordMatch]);
     }

  ngOnInit(): void {
    this.edgeService.getAllUsernames().subscribe( 
      (usernames) => {
        this.existingUsernames = usernames;
        this.usernameInput = new FormControl(this.user.username, [Validators.required, 
          CustomValidators.usernameExist(this.existingUsernames,this.user.username)]);
      })
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  modify() {
    if (this.user.id !== null){
      if (this.registerForm.value.profilePicture === '' || this.registerForm.value.profilePicture === null){
        this.registerForm.value.profilePicture = 'https://icon-library.com/images/profile-png-icon/profile-png-icon-24.jpg';
      }

      this.edgeService.modifyUser(this.user.id,this.registerForm.value.username, this.registerForm.value.password, 
        this.registerForm.value.firstName, this.registerForm.value.lastName, this.registerForm.value.birthDate,
        this.registerForm.value.email,this.registerForm.value.profilePicture).subscribe(
        (user: User) => {

        localStorage.removeItem('currentUser');
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.user = JSON.parse(localStorage.getItem("currentUser") as string);
        this.dialogRef.close(this.user);
      },
      (error) => {
        alert('Modification failed');
        console.log(error);
      }
    );
    }
  }

}
