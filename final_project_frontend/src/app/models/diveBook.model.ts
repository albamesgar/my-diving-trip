import { Dive } from "./dive.model";

export class DiveBook{
    constructor(
        private _id: number | null,
        private _userId: number,
        private _dives: Dive[]
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
    public get dives(): Dive[] {
        return this._dives;
    }
    public set dives(value: Dive[]) {
        this._dives = value;
    }

    public toJSON(){
        return{
            id: this.id,
            userId: this.userId,
            dives: this.dives
        }
    }
}