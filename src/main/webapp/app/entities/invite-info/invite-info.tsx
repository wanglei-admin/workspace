import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { byteSize, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './invite-info.reducer';
import { IInviteInfo } from 'app/shared/model/invite-info.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IInviteInfoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const InviteInfo = (props: IInviteInfoProps) => {
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

  const { inviteInfoList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="invite-info-heading">
        Invite Infos
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Invite Info
        </Link>
      </h2>
      <div className="table-responsive">
        {inviteInfoList && inviteInfoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('user')}>
                  User <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('post')}>
                  Post <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('workCity')}>
                  Work City <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('minSalary')}>
                  Min Salary <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('maxSalary')}>
                  Max Salary <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('education')}>
                  Education <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('specialty')}>
                  Specialty <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('graduation')}>
                  Graduation <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('company')}>
                  Company <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mail')}>
                  Mail <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('submitTime')}>
                  Submit Time <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('raquirement')}>
                  Raquirement <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('remark')}>
                  Remark <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {inviteInfoList.map((inviteInfo, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${inviteInfo.id}`} color="link" size="sm">
                      {inviteInfo.id}
                    </Button>
                  </td>
                  <td>{inviteInfo.user}</td>
                  <td>{inviteInfo.post}</td>
                  <td>{inviteInfo.workCity}</td>
                  <td>{inviteInfo.minSalary}</td>
                  <td>{inviteInfo.maxSalary}</td>
                  <td>{inviteInfo.education}</td>
                  <td>{inviteInfo.specialty}</td>
                  <td>{inviteInfo.graduation}</td>
                  <td>{inviteInfo.company}</td>
                  <td>{inviteInfo.mail}</td>
                  <td>{inviteInfo.status}</td>
                  <td>
                    {inviteInfo.submitTime ? <TextFormat type="date" value={inviteInfo.submitTime} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{inviteInfo.raquirement}</td>
                  <td>{inviteInfo.remark}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${inviteInfo.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${inviteInfo.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${inviteInfo.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Invite Infos found</div>
        )}
      </div>
      {props.totalItems ? (
        <div className={inviteInfoList && inviteInfoList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ inviteInfo }: IRootState) => ({
  inviteInfoList: inviteInfo.entities,
  loading: inviteInfo.loading,
  totalItems: inviteInfo.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InviteInfo);
