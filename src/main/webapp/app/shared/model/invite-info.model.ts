import { Moment } from 'moment';

export interface IInviteInfo {
  id?: number;
  user?: string;
  post?: string;
  workCity?: string;
  minSalary?: number;
  maxSalary?: number;
  education?: string;
  specialty?: string;
  graduation?: number;
  company?: string;
  mail?: string;
  status?: string;
  submitTime?: string;
  raquirement?: any;
  remark?: string;
}

export const defaultValue: Readonly<IInviteInfo> = {};
