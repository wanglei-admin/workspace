import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './invite-info.reducer';
import { IInviteInfo } from 'app/shared/model/invite-info.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInviteInfoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InviteInfoDetail = (props: IInviteInfoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { inviteInfoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          InviteInfo [<b>{inviteInfoEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="user">User</span>
          </dt>
          <dd>{inviteInfoEntity.user}</dd>
          <dt>
            <span id="post">Post</span>
          </dt>
          <dd>{inviteInfoEntity.post}</dd>
          <dt>
            <span id="workCity">Work City</span>
          </dt>
          <dd>{inviteInfoEntity.workCity}</dd>
          <dt>
            <span id="minSalary">Min Salary</span>
          </dt>
          <dd>{inviteInfoEntity.minSalary}</dd>
          <dt>
            <span id="maxSalary">Max Salary</span>
          </dt>
          <dd>{inviteInfoEntity.maxSalary}</dd>
          <dt>
            <span id="education">Education</span>
          </dt>
          <dd>{inviteInfoEntity.education}</dd>
          <dt>
            <span id="specialty">Specialty</span>
          </dt>
          <dd>{inviteInfoEntity.specialty}</dd>
          <dt>
            <span id="graduation">Graduation</span>
          </dt>
          <dd>{inviteInfoEntity.graduation}</dd>
          <dt>
            <span id="company">Company</span>
          </dt>
          <dd>{inviteInfoEntity.company}</dd>
          <dt>
            <span id="mail">Mail</span>
          </dt>
          <dd>{inviteInfoEntity.mail}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{inviteInfoEntity.status}</dd>
          <dt>
            <span id="submitTime">Submit Time</span>
          </dt>
          <dd>
            {inviteInfoEntity.submitTime ? <TextFormat value={inviteInfoEntity.submitTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="raquirement">Raquirement</span>
          </dt>
          <dd>{inviteInfoEntity.raquirement}</dd>
          <dt>
            <span id="remark">Remark</span>
          </dt>
          <dd>{inviteInfoEntity.remark}</dd>
        </dl>
        <Button tag={Link} to="/invite-info" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/invite-info/${inviteInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ inviteInfo }: IRootState) => ({
  inviteInfoEntity: inviteInfo.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InviteInfoDetail);
