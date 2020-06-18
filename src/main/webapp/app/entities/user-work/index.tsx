import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserWork from './user-work';
import UserWorkDetail from './user-work-detail';
import UserWorkUpdate from './user-work-update';
import UserWorkDeleteDialog from './user-work-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UserWorkDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserWorkUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserWorkUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserWorkDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserWork} />
    </Switch>
  </>
);

export default Routes;
