// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** addLoveStory POST /api/love/story/add */
export async function addLoveStoryUsingPost(
  body: API.LoveStoryAddRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponselong>('/api/love/story/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** deleteLoveStory POST /api/love/story/delete */
export async function deleteLoveStoryUsingPost(
  body: API.DeleteRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseboolean>('/api/love/story/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getLoveStoryById GET /api/love/story/get */
export async function getLoveStoryByIdUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getLoveStoryByIdUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseLoveStory>('/api/love/story/get', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listLoveStory GET /api/love/story/list */
export async function listLoveStoryUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.listLoveStoryUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseListLoveStory>('/api/love/story/list', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listLoveStoryByPage GET /api/love/story/list/page */
export async function listLoveStoryByPageUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.listLoveStoryByPageUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageLoveStory>('/api/love/story/list/page', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** randomGenLoveStory GET /api/love/story/random/gen */
export async function randomGenLoveStoryUsingGet(options?: { [key: string]: any }) {
  return request<API.BaseResponseLoveStoryVO>('/api/love/story/random/gen', {
    method: 'GET',
    ...(options || {}),
  });
}

/** updateLoveStory POST /api/love/story/update */
export async function updateLoveStoryUsingPost(
  body: API.LoveStoryUpdateRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseboolean>('/api/love/story/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
