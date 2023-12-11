import {User} from "../user.model/user";
import {Tour} from "../tour.model/tour";
import {OrderENUM} from "./order-enum";

export class Order {
  constructor(
    public createdAt: Date,
    public id:number,
    public user:User,
    public tour:Tour,
    public price: number,
    public status:OrderENUM,
    )
  { }
}
