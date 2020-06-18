import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './user-edu.reducer';
import { IUserEdu } from 'app/shared/model/user-edu.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserEduUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserEduUpdate = (props: IUserEduUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { userEduEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/user-edu' + props.location.search);
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
        ...userEduEntity,
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
          <h2 id="jobApp.userEdu.home.createOrEditLabel">Create or edit a UserEdu</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : userEduEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="user-edu-id">ID</Label>
                  <AvInput id="user-edu-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="loginLabel" for="user-edu-login">
                  Login
                </Label>
                <AvField id="user-edu-login" type="text" name="login" />
              </AvGroup>
              <AvGroup>
                <Label id="gradeLabel" for="user-edu-grade">
                  Grade
                </Label>
                <AvField id="user-edu-grade" type="text" name="grade" />
              </AvGroup>
              <AvGroup>
                <Label id="startDateLabel" for="user-edu-startDate">
                  Start Date
                </Label>
                <AvField id="user-edu-startDate" type="date" className="form-control" name="startDate" />
              </AvGroup>
              <AvGroup>
                <Label id="endDateLabel" for="user-edu-endDate">
                  End Date
                </Label>
                <AvField id="user-edu-endDate" type="date" className="form-control" name="endDate" />
              </AvGroup>
              <AvGroup>
                <Label id="schoolLabel" for="user-edu-school">
                  School
                </Label>
                <AvField id="user-edu-school" type="text" name="school" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/user-edu" replace color="info">
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
  userEduEntity: storeState.userEdu.entity,
  loading: storeState.userEdu.loading,
  updating: storeState.userEdu.updating,
  updateSuccess: storeState.userEdu.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserEduUpdate);
