// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** addPost POST /api/ai/post/add */
export async function addPostUsingPost(body: API.PostAddRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponseLong_>('/api/ai/post/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** deletePost POST /api/ai/post/delete */
export async function deletePostUsingPost(
  body: API.DeleteRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/ai/post/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** editPost POST /api/ai/post/edit */
export async function editPostUsingPost(
  body: API.PostEditRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/ai/post/edit', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** getPostVOById GET /api/ai/post/get/vo */
export async function getPostVoByIdUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getPostVOByIdUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePostVO_>('/api/ai/post/get/vo', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** listPostByPage POST /api/ai/post/list/page */
export async function listPostByPageUsingPost(
  body: API.PostQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePagePost_>('/api/ai/post/list/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listPostVOByPage POST /api/ai/post/list/page/vo */
export async function listPostVoByPageUsingPost(
  body: API.PostQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePagePostVO_>('/api/ai/post/list/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listMyPostVOByPage POST /api/ai/post/my/list/page/vo */
export async function listMyPostVoByPageUsingPost(
  body: API.PostQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePagePostVO_>('/api/ai/post/my/list/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** searchPostVOByPage POST /api/ai/post/search/page/vo */
export async function searchPostVoByPageUsingPost(
  body: API.PostQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePagePostVO_>('/api/ai/post/search/page/vo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** updatePost POST /api/ai/post/update */
export async function updatePostUsingPost(
  body: API.PostUpdateRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/ai/post/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
