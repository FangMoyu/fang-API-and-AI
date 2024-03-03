// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** getRandomLoveStory POST /api/ai/love/story/random/gen */
export async function getRandomLoveStoryUsingPost(options?: { [key: string]: any }) {
  return request<API.LoveStoryVO>('/api/ai/love/story/random/gen', {
    method: 'POST',
    ...(options || {}),
  });
}
