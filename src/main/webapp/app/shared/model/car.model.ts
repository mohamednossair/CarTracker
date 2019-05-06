import { IEmployeeCars } from 'app/shared/model/employee-cars.model';

export interface ICar {
  id?: number;
  code?: string;
  carType?: string;
  category?: string;
  serialNum?: string;
  hide?: boolean;
  isNew?: boolean;
  employeeCars?: IEmployeeCars[];
}

export class Car implements ICar {
  constructor(
    public id?: number,
    public code?: string,
    public carType?: string,
    public category?: string,
    public serialNum?: string,
    public hide?: boolean,
    public isNew?: boolean,
    public employeeCars?: IEmployeeCars[]
  ) {
    this.hide = this.hide || false;
    this.isNew = this.isNew || false;
  }
}
