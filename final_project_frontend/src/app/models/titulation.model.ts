import { Passport } from "./passport.model";

export class Titulation{
    constructor(
        private _id: number | null,
        private _organization: string,
        private _titleName: string,
        private _dateObtained: Date,
        private _instructorName: string,
        private _clubId: number,
        private _passport: Passport
    ){}

    public get passport(): Passport {
        return this._passport;
    }
    public set passport(value: Passport) {
        this._passport = value;
    }
    public get clubId(): number {
        return this._clubId;
    }
    public set clubId(value: number) {
        this._clubId = value;
    }
    public get instructorName(): string {
        return this._instructorName;
    }
    public set instructorName(value: string) {
        this._instructorName = value;
    }
    public get dateObtained(): Date {
        return this._dateObtained;
    }
    public set dateObtained(value: Date) {
        this._dateObtained = value;
    }
    public get titleName(): string {
        return this._titleName;
    }
    public set titleName(value: string) {
        this._titleName = value;
    }
    public get organization(): string {
        return this._organization;
    }
    public set organization(value: string) {
        this._organization = value;
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
            organization: this._organization,
            titleName: this._titleName,
            dateObtained: this._dateObtained,
            instructorName: this._instructorName,
            clubId: this._clubId,
            passport: this._passport
        }
    }
}