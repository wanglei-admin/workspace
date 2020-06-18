import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './user-info.reducer';
import { IUserInfo } from 'app/shared/model/user-info.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IUserInfoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const UserInfo = (props: IUserInfoProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { userInfoList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="user-info-heading">
        User Infos
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new User Info
        </Link>
      </h2>
      <div className="table-responsive">
        {userInfoList && userInfoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('login')}>
                  Login <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gender')}>
                  Gender <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('birthday')}>
                  Birthday <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mobile')}>
                  Mobile <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mail')}>
                  Mail <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('idno')}>
                  Idno <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('school')}>
                  School <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('specialty')}>
                  Specialty <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('education')}>
                  Education <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('graduation')}>
                  Graduation <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('company')}>
                  Company <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('companyMail')}>
                  Company Mail <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mailValidated')}>
                  Mail Validated <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('companyMailValidated')}>
                  Company Mail Validated <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userInfoList.map((userInfo, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${userInfo.id}`} color="link" size="sm">
                      {userInfo.id}
                    </Button>
                  </td>
                  <td>{userInfo.login}</td>
                  <td>{userInfo.name}</td>
                  <td>{userInfo.gender}</td>
                  <td>{userInfo.birthday ? <TextFormat type="date" value={userInfo.birthday} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{userInfo.mobile}</td>
                  <td>{userInfo.mail}</td>
                  <td>{userInfo.idno}</td>
                  <td>{userInfo.school}</td>
                  <td>{userInfo.specialty}</td>
                  <td>{userInfo.education}</td>
                  <td>{userInfo.graduation}</td>
                  <td>{userInfo.company}</td>
                  <td>{userInfo.companyMail}</td>
                  <td>{userInfo.mailValidated ? 'true' : 'false'}</td>
                  <td>{userInfo.companyMailValidated ? 'true' : 'false'}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${userInfo.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${userInfo.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${userInfo.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No User Infos found</div>
        )}
      </div>
      {props.totalItems ? (
        <div className={userInfoList && userInfoList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ userInfo }: IRootState) => ({
  userInfoList: userInfo.entities,
  loading: userInfo.loading,
  totalItems: userInfo.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserInfo);
