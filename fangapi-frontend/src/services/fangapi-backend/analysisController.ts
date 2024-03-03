// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** listTopInterfaceInfo GET /api/analysis/listTopInvokeInterfaceInfo */
export async function listTopInterfaceInfoUsingGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.listTopInterfaceInfoUsingGETParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseListInterfaceInfoVO>('/api/analysis/listTopInvokeInterfaceInfo', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}
