import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { EdgeService } from 'src/app/services/edge.service';
import { CustomValidators } from 'src/app/utils/custom.validations';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { MY_DATE_FORMATS } from 'src/app/utils/my-date-formats';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS }
  ]
}) 
export class RegisterComponent implements OnInit {

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
    private router: Router
  ) {
    this.existingUsernames=[];

    this.usernameInput = new FormControl('', [Validators.required, CustomValidators.usernameExist(this.existingUsernames,'')]);
    this.passwordInput = new FormControl('', [Validators.required]);
    this.passwordConfirmationInput = new FormControl('', [Validators.required]);
    this.firstNameInput = new FormControl('',[Validators.required]);
    this.lastNameInput = new FormControl('', [Validators.required])
    this.emailInput = new FormControl('', [Validators.required, Validators.email]);
    this.birthDateInput = new FormControl('',[Validators.required]);
    this.profilePictureInput = new FormControl('');

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
        
        this.usernameInput = new FormControl('', [Validators.required, CustomValidators.usernameExist(this.existingUsernames,'')]);
        this.passwordInput = new FormControl('', [Validators.required]);
        this.passwordConfirmationInput = new FormControl('', [Validators.required]);
        this.firstNameInput = new FormControl('',[Validators.required]);
        this.lastNameInput = new FormControl('', [Validators.required])
        this.emailInput = new FormControl('', [Validators.required, Validators.email]);
        this.birthDateInput = new FormControl('',[Validators.required]);
        this.profilePictureInput = new FormControl('');

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
      })
  }

  register() {
    if (this.registerForm.value.profilePicture === '' || this.registerForm.value.profilePicture === null){
      this.registerForm.value.profilePicture = 'https://icon-library.com/images/profile-png-icon/profile-png-icon-24.jpg';
    }
    this.edgeService.registerUser(this.registerForm.value.username, this.registerForm.value.password,this.registerForm.value.firstName,
      this.registerForm.value.lastName, this.registerForm.value.birthDate, this.registerForm.value.email,
      this.registerForm.value.profilePicture).subscribe(
      (user: User) => {
        console.log(user);

        // Redirect to home page
        this.router.navigate(['/login']);
      },
      (error) => {
        alert('Register failed');
        console.log(error);
      }
    );
  }
}
