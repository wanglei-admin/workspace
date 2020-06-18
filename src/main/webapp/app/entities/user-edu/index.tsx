import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserEdu from './user-edu';
import UserEduDetail from './user-edu-detail';
import UserEduUpdate from './user-edu-update';
import UserEduDeleteDialog from './user-edu-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UserEduDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserEduUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserEduUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserEduDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserEdu} />
    </Switch>
  </>
);

export default Routes;
