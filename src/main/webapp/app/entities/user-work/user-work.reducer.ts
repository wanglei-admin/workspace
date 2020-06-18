import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserWork, defaultValue } from 'app/shared/model/user-work.model';

export const ACTION_TYPES = {
  FETCH_USERWORK_LIST: 'userWork/FETCH_USERWORK_LIST',
  FETCH_USERWORK: 'userWork/FETCH_USERWORK',
  CREATE_USERWORK: 'userWork/CREATE_USERWORK',
  UPDATE_USERWORK: 'userWork/UPDATE_USERWORK',
  DELETE_USERWORK: 'userWork/DELETE_USERWORK',
  RESET: 'userWork/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserWork>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type UserWorkState = Readonly<typeof initialState>;

// Reducer

export default (state: UserWorkState = initialState, action): UserWorkState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USERWORK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERWORK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_USERWORK):
    case REQUEST(ACTION_TYPES.UPDATE_USERWORK):
    case REQUEST(ACTION_TYPES.DELETE_USERWORK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_USERWORK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERWORK):
    case FAILURE(ACTION_TYPES.CREATE_USERWORK):
    case FAILURE(ACTION_TYPES.UPDATE_USERWORK):
    case FAILURE(ACTION_TYPES.DELETE_USERWORK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERWORK_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERWORK):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERWORK):
    case SUCCESS(ACTION_TYPES.UPDATE_USERWORK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERWORK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/user-works';

// Actions

export const getEntities: ICrudGetAllAction<IUserWork> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_USERWORK_LIST,
    payload: axios.get<IUserWork>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IUserWork> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERWORK,
    payload: axios.get<IUserWork>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IUserWork> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERWORK,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUserWork> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERWORK,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserWork> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERWORK,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
