import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IInviteInfo, defaultValue } from 'app/shared/model/invite-info.model';

export const ACTION_TYPES = {
  FETCH_INVITEINFO_LIST: 'inviteInfo/FETCH_INVITEINFO_LIST',
  FETCH_INVITEINFO: 'inviteInfo/FETCH_INVITEINFO',
  CREATE_INVITEINFO: 'inviteInfo/CREATE_INVITEINFO',
  UPDATE_INVITEINFO: 'inviteInfo/UPDATE_INVITEINFO',
  DELETE_INVITEINFO: 'inviteInfo/DELETE_INVITEINFO',
  SET_BLOB: 'inviteInfo/SET_BLOB',
  RESET: 'inviteInfo/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IInviteInfo>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type InviteInfoState = Readonly<typeof initialState>;

// Reducer

export default (state: InviteInfoState = initialState, action): InviteInfoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_INVITEINFO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_INVITEINFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_INVITEINFO):
    case REQUEST(ACTION_TYPES.UPDATE_INVITEINFO):
    case REQUEST(ACTION_TYPES.DELETE_INVITEINFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_INVITEINFO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_INVITEINFO):
    case FAILURE(ACTION_TYPES.CREATE_INVITEINFO):
    case FAILURE(ACTION_TYPES.UPDATE_INVITEINFO):
    case FAILURE(ACTION_TYPES.DELETE_INVITEINFO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_INVITEINFO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_INVITEINFO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_INVITEINFO):
    case SUCCESS(ACTION_TYPES.UPDATE_INVITEINFO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_INVITEINFO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/invite-infos';

// Actions

export const getEntities: ICrudGetAllAction<IInviteInfo> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_INVITEINFO_LIST,
    payload: axios.get<IInviteInfo>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IInviteInfo> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_INVITEINFO,
    payload: axios.get<IInviteInfo>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IInviteInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_INVITEINFO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IInviteInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_INVITEINFO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IInviteInfo> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_INVITEINFO,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
