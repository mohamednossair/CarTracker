import { IEmployee } from 'app/shared/model/employee.model';

export interface ICompany {
  id?: number;
  code?: string;
  name?: string;
  employees?: IEmployee[];
}

export class Company implements ICompany {
  constructor(public id?: number, public code?: string, public name?: string, public employees?: IEmployee[]) {}
}
