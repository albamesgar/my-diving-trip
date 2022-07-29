import { DiveBook } from "./diveBook.model";

export class Dive{
    
    constructor(
        private _id: number | null,
        private _date: Date,
        private _location: string,
        private _maxDepth: number,
        private _minutesIn: number,
        private _airEntering: number | null,
        private _airOutgoing: number | null,
        private _temperature: number | null,
        private _visibility: number | null,
        private _bottleCapacity: string | null,
        private _airType: string | null,
        private _oxygenProportion: number | null,
        private _partnerName: string,
        private _partnerTitulation: string,
        private _clubId: number | null,
        private _rating: number | null,
        private _observations: string | null,
        private _picture: string | null,
        private _clubValidation: boolean,
        private _diveBook: DiveBook
    ){}

    public get clubValidation(): boolean {
        return this._clubValidation;
    }
    public set clubValidation(value: boolean) {
        this._clubValidation = value;
    }

    public get diveBook(): DiveBook {
        return this._diveBook;
    }
    public set diveBook(value: DiveBook) {
        this._diveBook = value;
    }
    public get picture(): string | null{
        return this._picture;
    }
    public set picture(value: string | null) {
        this._picture = value;
    }
    public get observations(): string | null {
        return this._observations;
    }
    public set observations(value: string | null) {
        this._observations = value;
    }
    public get rating(): number | null {
        return this._rating;
    }
    public set rating(value: number | null) {
        this._rating = value;
    }
    public get clubId(): number | null {
        return this._clubId;
    }
    public set clubId(value: number | null) {
        this._clubId = value;
    }
    public get partnerTitulation(): string {
        return this._partnerTitulation;
    }
    public set partnerTitulation(value: string) {
        this._partnerTitulation = value;
    }
    public get partnerName(): string {
        return this._partnerName;
    }
    public set partnerName(value: string) {
        this._partnerName = value;
    }
    public get oxygenProportion(): number | null {
        return this._oxygenProportion;
    }
    public set oxygenProportion(value: number | null) {
        this._oxygenProportion = value;
    }
    public get airType(): string | null {
        return this._airType;
    }
    public set airType(value: string | null) {
        this._airType = value;
    }
    public get bottleCapacity(): string | null {
        return this._bottleCapacity;
    }
    public set bottleCapacity(value: string | null) {
        this._bottleCapacity = value;
    }
    public get visibility(): number | null {
        return this._visibility;
    }
    public set visibility(value: number | null) {
        this._visibility = value;
    }
    public get temperature(): number | null {
        return this._temperature;
    }
    public set temperature(value: number | null) {
        this._temperature = value;
    }
    public get airOutgoing(): number | null {
        return this._airOutgoing;
    }
    public set airOutgoing(value: number | null) {
        this._airOutgoing = value;
    }
    public get airEntering(): number | null {
        return this._airEntering;
    }
    public set airEntering(value: number | null) {
        this._airEntering = value;
    }
    public get minutesIn(): number {
        return this._minutesIn;
    }
    public set minutesIn(value: number) {
        this._minutesIn = value;
    }
    public get maxDepth(): number {
        return this._maxDepth;
    }
    public set maxDepth(value: number) {
        this._maxDepth = value;
    }
    public get location(): string {
        return this._location;
    }
    public set location(value: string) {
        this._location = value;
    }
    public get date(): Date {
        return this._date;
    }
    public set date(value: Date) {
        this._date = value;
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
        date: this._date,
        location: this._location,
        maxDepth: this._maxDepth,
        minutesIn: this._minutesIn,
        airEntering: this._airEntering,
        airOutgoing: this._airOutgoing,
        temperature: this._temperature,
        visibility: this._visibility,
        bottleCapacity: this._bottleCapacity,
        airType: this._airType,
        oxygenProportion: this._oxygenProportion,
        partnerName: this._partnerName,
        partnerTitulation: this._partnerTitulation,
        clubId: this._clubId,
        rating: this._rating,
        observations: this._observations,
        picture: this._picture,
        clubValidation: this._clubValidation,
        diveBook: this._diveBook
        }
      }
}