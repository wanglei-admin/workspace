import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InviteInfo from './invite-info';
import InviteInfoDetail from './invite-info-detail';
import InviteInfoUpdate from './invite-info-update';
import InviteInfoDeleteDialog from './invite-info-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InviteInfoDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InviteInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InviteInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InviteInfoDetail} />
      <ErrorBoundaryRoute path={match.url} component={InviteInfo} />
    </Switch>
  </>
);

export default Routes;
