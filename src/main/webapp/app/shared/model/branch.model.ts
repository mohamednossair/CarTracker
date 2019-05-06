import { IEmployee } from 'app/shared/model/employee.model';

export interface IBranch {
  id?: number;
  code?: string;
  name?: string;
  employees?: IEmployee[];
}

export class Branch implements IBranch {
  constructor(public id?: number, public code?: string, public name?: string, public employees?: IEmployee[]) {}
}
