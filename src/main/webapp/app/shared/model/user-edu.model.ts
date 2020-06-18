import { Moment } from 'moment';

export interface IUserEdu {
  id?: number;
  login?: string;
  grade?: string;
  startDate?: string;
  endDate?: string;
  school?: string;
}

export const defaultValue: Readonly<IUserEdu> = {};
