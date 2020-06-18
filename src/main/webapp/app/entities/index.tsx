import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserInfo from './user-info';
import UserEdu from './user-edu';
import UserWork from './user-work';
import Company from './company';
import ApplyInfo from './apply-info';
import InviteInfo from './invite-info';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}user-info`} component={UserInfo} />
      <ErrorBoundaryRoute path={`${match.url}user-edu`} component={UserEdu} />
      <ErrorBoundaryRoute path={`${match.url}user-work`} component={UserWork} />
      <ErrorBoundaryRoute path={`${match.url}company`} component={Company} />
      <ErrorBoundaryRoute path={`${match.url}apply-info`} component={ApplyInfo} />
      <ErrorBoundaryRoute path={`${match.url}invite-info`} component={InviteInfo} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
