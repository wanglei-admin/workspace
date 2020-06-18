import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './invite-info.reducer';
import { IInviteInfo } from 'app/shared/model/invite-info.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInviteInfoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InviteInfoUpdate = (props: IInviteInfoUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { inviteInfoEntity, loading, updating } = props;

  const { raquirement } = inviteInfoEntity;

  const handleClose = () => {
    props.history.push('/invite-info' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.submitTime = convertDateTimeToServer(values.submitTime);

    if (errors.length === 0) {
      const entity = {
        ...inviteInfoEntity,
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
          <h2 id="jobApp.inviteInfo.home.createOrEditLabel">Create or edit a InviteInfo</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : inviteInfoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="invite-info-id">ID</Label>
                  <AvInput id="invite-info-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="userLabel" for="invite-info-user">
                  User
                </Label>
                <AvField id="invite-info-user" type="text" name="user" />
              </AvGroup>
              <AvGroup>
                <Label id="postLabel" for="invite-info-post">
                  Post
                </Label>
                <AvField id="invite-info-post" type="text" name="post" />
              </AvGroup>
              <AvGroup>
                <Label id="workCityLabel" for="invite-info-workCity">
                  Work City
                </Label>
                <AvField id="invite-info-workCity" type="text" name="workCity" />
              </AvGroup>
              <AvGroup>
                <Label id="minSalaryLabel" for="invite-info-minSalary">
                  Min Salary
                </Label>
                <AvField id="invite-info-minSalary" type="string" className="form-control" name="minSalary" />
              </AvGroup>
              <AvGroup>
                <Label id="maxSalaryLabel" for="invite-info-maxSalary">
                  Max Salary
                </Label>
                <AvField id="invite-info-maxSalary" type="string" className="form-control" name="maxSalary" />
              </AvGroup>
              <AvGroup>
                <Label id="educationLabel" for="invite-info-education">
                  Education
                </Label>
                <AvField id="invite-info-education" type="text" name="education" />
              </AvGroup>
              <AvGroup>
                <Label id="specialtyLabel" for="invite-info-specialty">
                  Specialty
                </Label>
                <AvField id="invite-info-specialty" type="text" name="specialty" />
              </AvGroup>
              <AvGroup>
                <Label id="graduationLabel" for="invite-info-graduation">
                  Graduation
                </Label>
                <AvField id="invite-info-graduation" type="string" className="form-control" name="graduation" />
              </AvGroup>
              <AvGroup>
                <Label id="companyLabel" for="invite-info-company">
                  Company
                </Label>
                <AvField id="invite-info-company" type="text" name="company" />
              </AvGroup>
              <AvGroup>
                <Label id="mailLabel" for="invite-info-mail">
                  Mail
                </Label>
                <AvField id="invite-info-mail" type="text" name="mail" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="invite-info-status">
                  Status
                </Label>
                <AvField id="invite-info-status" type="text" name="status" />
              </AvGroup>
              <AvGroup>
                <Label id="submitTimeLabel" for="invite-info-submitTime">
                  Submit Time
                </Label>
                <AvInput
                  id="invite-info-submitTime"
                  type="datetime-local"
                  className="form-control"
                  name="submitTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.inviteInfoEntity.submitTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="raquirementLabel" for="invite-info-raquirement">
                  Raquirement
                </Label>
                <AvInput id="invite-info-raquirement" type="textarea" name="raquirement" />
              </AvGroup>
              <AvGroup>
                <Label id="remarkLabel" for="invite-info-remark">
                  Remark
                </Label>
                <AvField id="invite-info-remark" type="text" name="remark" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/invite-info" replace color="info">
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
  inviteInfoEntity: storeState.inviteInfo.entity,
  loading: storeState.inviteInfo.loading,
  updating: storeState.inviteInfo.updating,
  updateSuccess: storeState.inviteInfo.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InviteInfoUpdate);
