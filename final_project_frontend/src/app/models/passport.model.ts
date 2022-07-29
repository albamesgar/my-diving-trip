import { Titulation } from "./titulation.model";

export class Passport{
    constructor(
        private _id: number | null,
        private _userId: number,
        private _titulations: Titulation[]
    ){}

    public get userId(): number {
        return this._userId;
    }
    public set userId(value: number) {
        this._userId = value;
    }
    public get id(): number | null {
        return this._id;
    }
    public set id(value: number | null) {
        this._id = value;
    }
    public get titulations(): Titulation[] {
        return this._titulations;
    }
    public set titulations(value: Titulation[]) {
        this._titulations = value;
    }

    public toJSON(){
        return{
            id: this._id,
            userId: this._userId,
            titulations: this._titulations
        }
    }
}