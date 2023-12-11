import {Location} from "../location.model/location";

export class User {
  constructor(
    public id: number | undefined,
    public firstName: string,
    public lastName: string,
    public email: string,
    public phoneNumber: string,
    public password: string,
    public location : Location,
    public locationId?: number)
  { }
}
