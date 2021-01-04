import { Moment } from 'moment';

export interface IEmployeeInfo {
  id?: number;
  empName?: string;
  designation?: string;
  mobile?: string;
  dob?: Moment;
  remarks?: string;
}

export class EmployeeInfo implements IEmployeeInfo {
  constructor(
    public id?: number,
    public empName?: string,
    public designation?: string,
    public mobile?: string,
    public dob?: Moment,
    public remarks?: string
  ) {}
}
