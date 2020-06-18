import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserEdu, defaultValue } from 'app/shared/model/user-edu.model';

export const ACTION_TYPES = {
  FETCH_USEREDU_LIST: 'userEdu/FETCH_USEREDU_LIST',
  FETCH_USEREDU: 'userEdu/FETCH_USEREDU',
  CREATE_USEREDU: 'userEdu/CREATE_USEREDU',
  UPDATE_USEREDU: 'userEdu/UPDATE_USEREDU',
  DELETE_USEREDU: 'userEdu/DELETE_USEREDU',
  RESET: 'userEdu/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserEdu>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type UserEduState = Readonly<typeof initialState>;

// Reducer

export default (state: UserEduState = initialState, action): UserEduState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USEREDU_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USEREDU):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_USEREDU):
    case REQUEST(ACTION_TYPES.UPDATE_USEREDU):
    case REQUEST(ACTION_TYPES.DELETE_USEREDU):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_USEREDU_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USEREDU):
    case FAILURE(ACTION_TYPES.CREATE_USEREDU):
    case FAILURE(ACTION_TYPES.UPDATE_USEREDU):
    case FAILURE(ACTION_TYPES.DELETE_USEREDU):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USEREDU_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_USEREDU):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_USEREDU):
    case SUCCESS(ACTION_TYPES.UPDATE_USEREDU):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_USEREDU):
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

const apiUrl = 'api/user-edus';

// Actions

export const getEntities: ICrudGetAllAction<IUserEdu> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_USEREDU_LIST,
    payload: axios.get<IUserEdu>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IUserEdu> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USEREDU,
    payload: axios.get<IUserEdu>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IUserEdu> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USEREDU,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUserEdu> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USEREDU,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserEdu> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USEREDU,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
