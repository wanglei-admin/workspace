import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ApplyInfo from './apply-info';
import ApplyInfoDetail from './apply-info-detail';
import ApplyInfoUpdate from './apply-info-update';
import ApplyInfoDeleteDialog from './apply-info-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ApplyInfoDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ApplyInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ApplyInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ApplyInfoDetail} />
      <ErrorBoundaryRoute path={match.url} component={ApplyInfo} />
    </Switch>
  </>
);

export default Routes;
