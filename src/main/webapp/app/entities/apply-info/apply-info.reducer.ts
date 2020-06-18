import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IApplyInfo, defaultValue } from 'app/shared/model/apply-info.model';

export const ACTION_TYPES = {
  FETCH_APPLYINFO_LIST: 'applyInfo/FETCH_APPLYINFO_LIST',
  FETCH_APPLYINFO: 'applyInfo/FETCH_APPLYINFO',
  CREATE_APPLYINFO: 'applyInfo/CREATE_APPLYINFO',
  UPDATE_APPLYINFO: 'applyInfo/UPDATE_APPLYINFO',
  DELETE_APPLYINFO: 'applyInfo/DELETE_APPLYINFO',
  RESET: 'applyInfo/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IApplyInfo>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ApplyInfoState = Readonly<typeof initialState>;

// Reducer

export default (state: ApplyInfoState = initialState, action): ApplyInfoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_APPLYINFO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_APPLYINFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_APPLYINFO):
    case REQUEST(ACTION_TYPES.UPDATE_APPLYINFO):
    case REQUEST(ACTION_TYPES.DELETE_APPLYINFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_APPLYINFO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_APPLYINFO):
    case FAILURE(ACTION_TYPES.CREATE_APPLYINFO):
    case FAILURE(ACTION_TYPES.UPDATE_APPLYINFO):
    case FAILURE(ACTION_TYPES.DELETE_APPLYINFO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_APPLYINFO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_APPLYINFO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_APPLYINFO):
    case SUCCESS(ACTION_TYPES.UPDATE_APPLYINFO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_APPLYINFO):
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

const apiUrl = 'api/apply-infos';

// Actions

export const getEntities: ICrudGetAllAction<IApplyInfo> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_APPLYINFO_LIST,
    payload: axios.get<IApplyInfo>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IApplyInfo> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_APPLYINFO,
    payload: axios.get<IApplyInfo>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IApplyInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_APPLYINFO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IApplyInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_APPLYINFO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IApplyInfo> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_APPLYINFO,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
