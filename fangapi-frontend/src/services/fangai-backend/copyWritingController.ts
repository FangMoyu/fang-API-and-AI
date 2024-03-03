// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** genCopyWritingContext POST /api/ai/copyWriting/gen */
export async function genCopyWritingContextUsingPost(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.genCopyWritingContextUsingPOSTParams,
  options?: { [key: string]: any },
) {
  return request<API.CopyWritingResponse>('/api/ai/copyWriting/gen', {
    method: 'POST',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}
