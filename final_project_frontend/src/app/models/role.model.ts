import { User } from "./user.model";

export class Role{
    constructor(
        private _id: number,
        private _name: string,
        private _users: User[]
    ){}

    public get users(): User[] {
        return this._users;
    }
    public set users(value: User[]) {
        this._users = value;
    }
    public get name(): string {
        return this._name;
    }
    public set name(value: string) {
        this._name = value;
    }
    public get id(): number {
        return this._id;
    }
    public set id(value: number) {
        this._id = value;
    }
    
    public toJSON(){
        return{
            id: this._id,
            name: this._name,
            userSet: this._users
        }
    }
}