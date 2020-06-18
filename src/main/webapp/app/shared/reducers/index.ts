import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import userInfo, {
  UserInfoState
} from 'app/entities/user-info/user-info.reducer';
// prettier-ignore
import userEdu, {
  UserEduState
} from 'app/entities/user-edu/user-edu.reducer';
// prettier-ignore
import userWork, {
  UserWorkState
} from 'app/entities/user-work/user-work.reducer';
// prettier-ignore
import company, {
  CompanyState
} from 'app/entities/company/company.reducer';
// prettier-ignore
import applyInfo, {
  ApplyInfoState
} from 'app/entities/apply-info/apply-info.reducer';
// prettier-ignore
import inviteInfo, {
  InviteInfoState
} from 'app/entities/invite-info/invite-info.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly userInfo: UserInfoState;
  readonly userEdu: UserEduState;
  readonly userWork: UserWorkState;
  readonly company: CompanyState;
  readonly applyInfo: ApplyInfoState;
  readonly inviteInfo: InviteInfoState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  userInfo,
  userEdu,
  userWork,
  company,
  applyInfo,
  inviteInfo,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
