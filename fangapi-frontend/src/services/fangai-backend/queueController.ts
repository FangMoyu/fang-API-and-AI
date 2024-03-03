// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** add GET /api/ai/queue/add */
export async function addUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.addUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<any>('/api/ai/queue/add', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** get GET /api/ai/queue/get */
export async function getUsingGet(options?: { [key: string]: any }) {
  return request<string>('/api/ai/queue/get', {
    method: 'GET',
    ...(options || {}),
  });
}
