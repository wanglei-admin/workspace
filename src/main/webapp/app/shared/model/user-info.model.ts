import { Moment } from 'moment';

export interface IUserInfo {
  id?: number;
  login?: string;
  name?: string;
  gender?: number;
  birthday?: string;
  mobile?: string;
  mail?: string;
  idno?: string;
  school?: string;
  specialty?: string;
  education?: string;
  graduation?: number;
  company?: string;
  companyMail?: string;
  mailValidated?: boolean;
  companyMailValidated?: boolean;
}

export const defaultValue: Readonly<IUserInfo> = {
  mailValidated: false,
  companyMailValidated: false,
};
