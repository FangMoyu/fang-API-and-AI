// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** listMyGenChartByPage POST /api/AiProduction/list/myChart */
export async function listMyGenChartByPageUsingPost(
  body: API.ChartQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageChart>('/api/AiProduction/list/myChart', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
