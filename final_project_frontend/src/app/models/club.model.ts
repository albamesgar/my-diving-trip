export class Club{
    constructor(
        private _id: number | null,
        private _name: string,
        private _street: string,
        private _homeNumber: number,
        private _city: string,
        private _postalCode: number,
        private _country: string,
        private _contactPhone: number,
        private _email: string,
        private _rating: number
    ){}

    public get contactPhone(): number {
        return this._contactPhone;
    }
    public set contactPhone(value: number) {
        this._contactPhone = value;
    }
    
    public get rating(): number {
        return this._rating;
    }
    public set rating(value: number) {
        this._rating = value;
    }
    public get email(): string {
        return this._email;
    }
    public set email(value: string) {
        this._email = value;
    }
    public get country(): string {
        return this._country;
    }
    public set country(value: string) {
        this._country = value;
    }
    public get postalCode(): number {
        return this._postalCode;
    }
    public set postalCode(value: number) {
        this._postalCode = value;
    }
    public get city(): string {
        return this._city;
    }
    public set city(value: string) {
        this._city = value;
    }
    public get homeNumber(): number {
        return this._homeNumber;
    }
    public set homeNumber(value: number) {
        this._homeNumber = value;
    }
    public get street(): string {
        return this._street;
    }
    public set street(value: string) {
        this._street = value;
    }
    public get name(): string {
        return this._name;
    }
    public set name(value: string) {
        this._name = value;
    }
    public get id(): number | null {
        return this._id;
    }
    public set id(value: number | null) {
        this._id = value;
    }
}