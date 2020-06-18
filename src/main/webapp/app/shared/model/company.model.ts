export interface ICompany {
  id?: number;
  name?: string;
  address?: string;
  mobile?: string;
  website?: string;
  mail?: string;
  status?: string;
  rgno?: string;
}

export const defaultValue: Readonly<ICompany> = {};
