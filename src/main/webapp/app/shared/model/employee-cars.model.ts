import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { ICar } from 'app/shared/model/car.model';

export interface IEmployeeCars {
  id?: number;
  previousReading?: number;
  currentReading?: number;
  workingDays?: number;
  updateDate?: Moment;
  employee?: IEmployee;
  car?: ICar;
}

export class EmployeeCars implements IEmployeeCars {
  constructor(
    public id?: number,
    public previousReading?: number,
    public currentReading?: number,
    public workingDays?: number,
    public updateDate?: Moment,
    public employee?: IEmployee,
    public car?: ICar
  ) {}
}
