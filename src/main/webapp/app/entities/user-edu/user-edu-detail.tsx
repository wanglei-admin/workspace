import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-edu.reducer';
import { IUserEdu } from 'app/shared/model/user-edu.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserEduDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserEduDetail = (props: IUserEduDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { userEduEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          UserEdu [<b>{userEduEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="login">Login</span>
          </dt>
          <dd>{userEduEntity.login}</dd>
          <dt>
            <span id="grade">Grade</span>
          </dt>
          <dd>{userEduEntity.grade}</dd>
          <dt>
            <span id="startDate">Start Date</span>
          </dt>
          <dd>
            {userEduEntity.startDate ? <TextFormat value={userEduEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>{userEduEntity.endDate ? <TextFormat value={userEduEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="school">School</span>
          </dt>
          <dd>{userEduEntity.school}</dd>
        </dl>
        <Button tag={Link} to="/user-edu" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-edu/${userEduEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ userEdu }: IRootState) => ({
  userEduEntity: userEdu.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserEduDetail);
