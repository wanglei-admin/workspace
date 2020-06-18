import { Moment } from 'moment';

export interface IApplyInfo {
  id?: number;
  user?: string;
  post?: string;
  expCity?: string;
  expSalary?: number;
  submitTime?: string;
  status?: string;
  education?: string;
  specialty?: string;
  graduation?: number;
}

export const defaultValue: Readonly<IApplyInfo> = {};
