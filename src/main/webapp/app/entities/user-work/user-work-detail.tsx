import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-work.reducer';
import { IUserWork } from 'app/shared/model/user-work.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserWorkDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserWorkDetail = (props: IUserWorkDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { userWorkEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          UserWork [<b>{userWorkEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="login">Login</span>
          </dt>
          <dd>{userWorkEntity.login}</dd>
          <dt>
            <span id="startDate">Start Date</span>
          </dt>
          <dd>
            {userWorkEntity.startDate ? <TextFormat value={userWorkEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>
            {userWorkEntity.endDate ? <TextFormat value={userWorkEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="company">Company</span>
          </dt>
          <dd>{userWorkEntity.company}</dd>
          <dt>
            <span id="post">Post</span>
          </dt>
          <dd>{userWorkEntity.post}</dd>
          <dt>
            <span id="salary">Salary</span>
          </dt>
          <dd>{userWorkEntity.salary}</dd>
        </dl>
        <Button tag={Link} to="/user-work" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-work/${userWorkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ userWork }: IRootState) => ({
  userWorkEntity: userWork.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserWorkDetail);
