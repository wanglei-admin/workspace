import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './user-work.reducer';
import { IUserWork } from 'app/shared/model/user-work.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserWorkUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserWorkUpdate = (props: IUserWorkUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { userWorkEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/user-work' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...userWorkEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jobApp.userWork.home.createOrEditLabel">Create or edit a UserWork</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : userWorkEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="user-work-id">ID</Label>
                  <AvInput id="user-work-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="loginLabel" for="user-work-login">
                  Login
                </Label>
                <AvField id="user-work-login" type="text" name="login" />
              </AvGroup>
              <AvGroup>
                <Label id="startDateLabel" for="user-work-startDate">
                  Start Date
                </Label>
                <AvField id="user-work-startDate" type="date" className="form-control" name="startDate" />
              </AvGroup>
              <AvGroup>
                <Label id="endDateLabel" for="user-work-endDate">
                  End Date
                </Label>
                <AvField id="user-work-endDate" type="date" className="form-control" name="endDate" />
              </AvGroup>
              <AvGroup>
                <Label id="companyLabel" for="user-work-company">
                  Company
                </Label>
                <AvField id="user-work-company" type="text" name="company" />
              </AvGroup>
              <AvGroup>
                <Label id="postLabel" for="user-work-post">
                  Post
                </Label>
                <AvField id="user-work-post" type="text" name="post" />
              </AvGroup>
              <AvGroup>
                <Label id="salaryLabel" for="user-work-salary">
                  Salary
                </Label>
                <AvField id="user-work-salary" type="string" className="form-control" name="salary" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/user-work" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  userWorkEntity: storeState.userWork.entity,
  loading: storeState.userWork.loading,
  updating: storeState.userWork.updating,
  updateSuccess: storeState.userWork.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserWorkUpdate);
