import { Moment } from 'moment';

export interface IUserWork {
  id?: number;
  login?: string;
  startDate?: string;
  endDate?: string;
  company?: string;
  post?: string;
  salary?: number;
}

export const defaultValue: Readonly<IUserWork> = {};
