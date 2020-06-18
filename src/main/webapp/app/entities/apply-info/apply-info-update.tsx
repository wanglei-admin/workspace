import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './apply-info.reducer';
import { IApplyInfo } from 'app/shared/model/apply-info.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IApplyInfoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ApplyInfoUpdate = (props: IApplyInfoUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { applyInfoEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/apply-info' + props.location.search);
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
    values.submitTime = convertDateTimeToServer(values.submitTime);

    if (errors.length === 0) {
      const entity = {
        ...applyInfoEntity,
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
          <h2 id="jobApp.applyInfo.home.createOrEditLabel">Create or edit a ApplyInfo</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : applyInfoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="apply-info-id">ID</Label>
                  <AvInput id="apply-info-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="userLabel" for="apply-info-user">
                  User
                </Label>
                <AvField id="apply-info-user" type="text" name="user" />
              </AvGroup>
              <AvGroup>
                <Label id="postLabel" for="apply-info-post">
                  Post
                </Label>
                <AvField id="apply-info-post" type="text" name="post" />
              </AvGroup>
              <AvGroup>
                <Label id="expCityLabel" for="apply-info-expCity">
                  Exp City
                </Label>
                <AvField id="apply-info-expCity" type="text" name="expCity" />
              </AvGroup>
              <AvGroup>
                <Label id="expSalaryLabel" for="apply-info-expSalary">
                  Exp Salary
                </Label>
                <AvField id="apply-info-expSalary" type="string" className="form-control" name="expSalary" />
              </AvGroup>
              <AvGroup>
                <Label id="submitTimeLabel" for="apply-info-submitTime">
                  Submit Time
                </Label>
                <AvInput
                  id="apply-info-submitTime"
                  type="datetime-local"
                  className="form-control"
                  name="submitTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.applyInfoEntity.submitTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="apply-info-status">
                  Status
                </Label>
                <AvField id="apply-info-status" type="text" name="status" />
              </AvGroup>
              <AvGroup>
                <Label id="educationLabel" for="apply-info-education">
                  Education
                </Label>
                <AvField id="apply-info-education" type="text" name="education" />
              </AvGroup>
              <AvGroup>
                <Label id="specialtyLabel" for="apply-info-specialty">
                  Specialty
                </Label>
                <AvField id="apply-info-specialty" type="text" name="specialty" />
              </AvGroup>
              <AvGroup>
                <Label id="graduationLabel" for="apply-info-graduation">
                  Graduation
                </Label>
                <AvField id="apply-info-graduation" type="string" className="form-control" name="graduation" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/apply-info" replace color="info">
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
  applyInfoEntity: storeState.applyInfo.entity,
  loading: storeState.applyInfo.loading,
  updating: storeState.applyInfo.updating,
  updateSuccess: storeState.applyInfo.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ApplyInfoUpdate);
