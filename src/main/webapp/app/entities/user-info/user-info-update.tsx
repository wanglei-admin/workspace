import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './user-info.reducer';
import { IUserInfo } from 'app/shared/model/user-info.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserInfoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserInfoUpdate = (props: IUserInfoUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { userInfoEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/user-info' + props.location.search);
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
    values.birthday = convertDateTimeToServer(values.birthday);

    if (errors.length === 0) {
      const entity = {
        ...userInfoEntity,
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
          <h2 id="jobApp.userInfo.home.createOrEditLabel">Create or edit a UserInfo</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : userInfoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="user-info-id">ID</Label>
                  <AvInput id="user-info-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="loginLabel" for="user-info-login">
                  Login
                </Label>
                <AvField id="user-info-login" type="text" name="login" />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="user-info-name">
                  Name
                </Label>
                <AvField id="user-info-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="genderLabel" for="user-info-gender">
                  Gender
                </Label>
                <AvField id="user-info-gender" type="string" className="form-control" name="gender" />
              </AvGroup>
              <AvGroup>
                <Label id="birthdayLabel" for="user-info-birthday">
                  Birthday
                </Label>
                <AvInput
                  id="user-info-birthday"
                  type="datetime-local"
                  className="form-control"
                  name="birthday"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.userInfoEntity.birthday)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="mobileLabel" for="user-info-mobile">
                  Mobile
                </Label>
                <AvField id="user-info-mobile" type="text" name="mobile" />
              </AvGroup>
              <AvGroup>
                <Label id="mailLabel" for="user-info-mail">
                  Mail
                </Label>
                <AvField id="user-info-mail" type="text" name="mail" />
              </AvGroup>
              <AvGroup>
                <Label id="idnoLabel" for="user-info-idno">
                  Idno
                </Label>
                <AvField id="user-info-idno" type="text" name="idno" />
              </AvGroup>
              <AvGroup>
                <Label id="schoolLabel" for="user-info-school">
                  School
                </Label>
                <AvField id="user-info-school" type="text" name="school" />
              </AvGroup>
              <AvGroup>
                <Label id="specialtyLabel" for="user-info-specialty">
                  Specialty
                </Label>
                <AvField id="user-info-specialty" type="text" name="specialty" />
              </AvGroup>
              <AvGroup>
                <Label id="educationLabel" for="user-info-education">
                  Education
                </Label>
                <AvField id="user-info-education" type="text" name="education" />
              </AvGroup>
              <AvGroup>
                <Label id="graduationLabel" for="user-info-graduation">
                  Graduation
                </Label>
                <AvField id="user-info-graduation" type="string" className="form-control" name="graduation" />
              </AvGroup>
              <AvGroup>
                <Label id="companyLabel" for="user-info-company">
                  Company
                </Label>
                <AvField id="user-info-company" type="text" name="company" />
              </AvGroup>
              <AvGroup>
                <Label id="companyMailLabel" for="user-info-companyMail">
                  Company Mail
                </Label>
                <AvField id="user-info-companyMail" type="text" name="companyMail" />
              </AvGroup>
              <AvGroup check>
                <Label id="mailValidatedLabel">
                  <AvInput id="user-info-mailValidated" type="checkbox" className="form-check-input" name="mailValidated" />
                  Mail Validated
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="companyMailValidatedLabel">
                  <AvInput id="user-info-companyMailValidated" type="checkbox" className="form-check-input" name="companyMailValidated" />
                  Company Mail Validated
                </Label>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/user-info" replace color="info">
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
  userInfoEntity: storeState.userInfo.entity,
  loading: storeState.userInfo.loading,
  updating: storeState.userInfo.updating,
  updateSuccess: storeState.userInfo.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserInfoUpdate);
