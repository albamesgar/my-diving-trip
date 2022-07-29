import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";
import { Club } from "../models/club.model";
import { EdgeService } from "../services/edge.service";

export class CustomValidators {

    static passwordMatch(group: AbstractControl<any, any>): ValidationErrors | null {
        const password = group.get('password')?.value;
        const passwordConfirmation = group.get('passwordConfirmation')?.value;

        if (password !== passwordConfirmation) {
            // passwordConfirmation.markAsInvalid();
            return {
                passwordMatch: true
            };
        }
        // todo OK
        return null;
    }

    static usernameExist(existingUsernames: string[], currentUsername: string): ValidatorFn{
        return (control: AbstractControl<any, any>): ValidationErrors | null => {;
            for (let existingUsername of existingUsernames){
                if (control.value === existingUsername && control.value !== currentUsername){
                    return {
                        usernameExist: true
                    }
                }
            };
            // todo OK
            return null;
        };
    }

    static ClubNoExist(clubs: Club[]): ValidatorFn{
        return (control: AbstractControl<any, any>): ValidationErrors | null => {;
            for (let club of clubs){
                const clubName = club.name;
                if (control.value === clubName){
                    return null;
                }
            };
            if (control.value === ''){
                return null;
            }
            return {
                ClubNoExist: true
            }
        };
    }
}