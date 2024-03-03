// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** invokeAiGenCopyWriting POST /api/GenAi/invoke/copy/writing */
export async function invokeAiGenCopyWritingUsingPost(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.invokeAiGenCopyWritingUsingPOSTParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsestring>('/api/GenAi/invoke/copy/writing', {
    method: 'POST',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** invokeAiGenChartInterface POST /api/GenAi/invokeAi */
export async function invokeAiGenChartInterfaceUsingPost(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.invokeAiGenChartInterfaceUsingPOSTParams,
  body: {},
  file?: File,
  options?: { [key: string]: any },
) {
  const formData = new FormData();

  if (file) {
    formData.append('file', file);
  }

  Object.keys(body).forEach((ele) => {
    const item = (body as any)[ele];

    if (item !== undefined && item !== null) {
      if (typeof item === 'object' && !(item instanceof File)) {
        if (item instanceof Array) {
          item.forEach((f) => formData.append(ele, f || ''));
        } else {
          formData.append(ele, JSON.stringify(item));
        }
      } else {
        formData.append(ele, item);
      }
    }
  });

  return request<API.BaseResponsestring>('/api/GenAi/invokeAi', {
    method: 'POST',
    params: {
      ...params,
    },
    data: formData,
    requestType: 'form',
    ...(options || {}),
  });
}