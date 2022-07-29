import { Role } from "./role.model";

export class User{
    constructor(
        private _id: number | null,
        private _username: string,
        private _password: string,
        private _firstName: string,
        private _lastName: string,
        private _birthDate: Date,
        private _email: string,
        private _profilePicture: string,
        private _role: Role | string
    ){}

    public get profilePicture(): string {
        return this._profilePicture;
    }
    public set profilePicture(value: string) {
        this._profilePicture = value;
    }
    public get lastName(): string {
        return this._lastName;
    }
    public set lastName(value: string) {
        this._lastName = value;
    }
    public get firstName(): string {
        return this._firstName;
    }
    public set firstName(value: string) {
        this._firstName = value;
    }
    
    public get role(): Role | string{
        return this._role;
    }
    public set role(value: Role | string) {
        this._role = value;
    }
    public get email(): string {
        return this._email;
    }
    public set email(value: string) {
        this._email = value;
    }
    public get birthDate(): Date {
        return this._birthDate;
    }
    public set birthDate(value: Date) {
        this._birthDate = value;
    }
    public get password(): string {
        return this._password;
    }
    public set password(value: string) {
        this._password = value;
    }
    public get username(): string {
        return this._username;
    }
    public set username(value: string) {
        this._username = value;
    }
    public get id(): number | null {
        return this._id;
    }
    public set id(value: number | null) {
        this._id = value;
    }

    public toJSON(){
        return {
            id: this._id,
            username: this._username,
            password: this._password,
            firstName: this._firstName,
            lastName: this._lastName,
            email: this._email,
            birthDate: this._birthDate,
            profilePicture: this._profilePicture,
            role: this._role
        }
    }
}