import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-info.reducer';
import { IUserInfo } from 'app/shared/model/user-info.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserInfoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserInfoDetail = (props: IUserInfoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { userInfoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          UserInfo [<b>{userInfoEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="login">Login</span>
          </dt>
          <dd>{userInfoEntity.login}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{userInfoEntity.name}</dd>
          <dt>
            <span id="gender">Gender</span>
          </dt>
          <dd>{userInfoEntity.gender}</dd>
          <dt>
            <span id="birthday">Birthday</span>
          </dt>
          <dd>{userInfoEntity.birthday ? <TextFormat value={userInfoEntity.birthday} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="mobile">Mobile</span>
          </dt>
          <dd>{userInfoEntity.mobile}</dd>
          <dt>
            <span id="mail">Mail</span>
          </dt>
          <dd>{userInfoEntity.mail}</dd>
          <dt>
            <span id="idno">Idno</span>
          </dt>
          <dd>{userInfoEntity.idno}</dd>
          <dt>
            <span id="school">School</span>
          </dt>
          <dd>{userInfoEntity.school}</dd>
          <dt>
            <span id="specialty">Specialty</span>
          </dt>
          <dd>{userInfoEntity.specialty}</dd>
          <dt>
            <span id="education">Education</span>
          </dt>
          <dd>{userInfoEntity.education}</dd>
          <dt>
            <span id="graduation">Graduation</span>
          </dt>
          <dd>{userInfoEntity.graduation}</dd>
          <dt>
            <span id="company">Company</span>
          </dt>
          <dd>{userInfoEntity.company}</dd>
          <dt>
            <span id="companyMail">Company Mail</span>
          </dt>
          <dd>{userInfoEntity.companyMail}</dd>
          <dt>
            <span id="mailValidated">Mail Validated</span>
          </dt>
          <dd>{userInfoEntity.mailValidated ? 'true' : 'false'}</dd>
          <dt>
            <span id="companyMailValidated">Company Mail Validated</span>
          </dt>
          <dd>{userInfoEntity.companyMailValidated ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/user-info" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-info/${userInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ userInfo }: IRootState) => ({
  userInfoEntity: userInfo.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserInfoDetail);
