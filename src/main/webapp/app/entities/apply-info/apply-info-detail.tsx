import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './apply-info.reducer';
import { IApplyInfo } from 'app/shared/model/apply-info.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IApplyInfoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ApplyInfoDetail = (props: IApplyInfoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { applyInfoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          ApplyInfo [<b>{applyInfoEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="user">User</span>
          </dt>
          <dd>{applyInfoEntity.user}</dd>
          <dt>
            <span id="post">Post</span>
          </dt>
          <dd>{applyInfoEntity.post}</dd>
          <dt>
            <span id="expCity">Exp City</span>
          </dt>
          <dd>{applyInfoEntity.expCity}</dd>
          <dt>
            <span id="expSalary">Exp Salary</span>
          </dt>
          <dd>{applyInfoEntity.expSalary}</dd>
          <dt>
            <span id="submitTime">Submit Time</span>
          </dt>
          <dd>
            {applyInfoEntity.submitTime ? <TextFormat value={applyInfoEntity.submitTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{applyInfoEntity.status}</dd>
          <dt>
            <span id="education">Education</span>
          </dt>
          <dd>{applyInfoEntity.education}</dd>
          <dt>
            <span id="specialty">Specialty</span>
          </dt>
          <dd>{applyInfoEntity.specialty}</dd>
          <dt>
            <span id="graduation">Graduation</span>
          </dt>
          <dd>{applyInfoEntity.graduation}</dd>
        </dl>
        <Button tag={Link} to="/apply-info" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/apply-info/${applyInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ applyInfo }: IRootState) => ({
  applyInfoEntity: applyInfo.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ApplyInfoDetail);
