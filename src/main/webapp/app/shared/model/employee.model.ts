import { IUser } from 'app/core/user/user.model';
import { IEmployeeCars } from 'app/shared/model/employee-cars.model';
import { IBranch } from 'app/shared/model/branch.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IEmployee {
  id?: number;
  code?: string;
  name?: string;
  user?: IUser;
  employeeCars?: IEmployeeCars[];
  branche?: IBranch;
  company?: ICompany;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public user?: IUser,
    public employeeCars?: IEmployeeCars[],
    public branche?: IBranch,
    public company?: ICompany
  ) {}
}
