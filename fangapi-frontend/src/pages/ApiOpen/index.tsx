import { PageContainer } from '@ant-design/pro-components';
import {Button, Card, message,} from 'antd';

import React, { useEffect, useState } from 'react';

import {changeUserApiUsingPost, getUserAccessUsingGet} from "@/services/fangapi-backend/userController";
import copy from "copy-to-clipboard";
import {SnippetsTwoTone} from "@ant-design/icons";

/**
 * 主页
 * @constructor
 */
const UserAPI: React.FC = () => {
  // 定义状态和钩子函数

  const [data, setData] = useState<API.UserAccessVO>();
  const [isComplete, setIsComplete] = useState<boolean>(false);

  const loadData = async () => {

    try {
      // 发起请求获取接口信息，接受一个包含 id 参数的对象作为参数
      const res = await getUserAccessUsingGet();
      if(res.code === 0) {
        // 将获取到的接口信息设置到 data 状态中
        setData(res.data);
        console.log(res.data);
        setIsComplete(true);
      }
    } catch (error: any) {
      // 请求失败处理
      message.error('请求失败，' + error.message);
    }

  };

  useEffect(() => {
    loadData();
  }, [isComplete]);


    const handleCopy = (key) => {
      copy(data[key]);
      message.success("复制成功");
      // 可以添加一些提示信息，告诉用户复制成功
    }
  const ChangeApi = async () => {
    setIsComplete(false);
      try{
        const res = await changeUserApiUsingPost();
        if(res.code === 0) {
          message.success("生成成功");

        }
      }catch (error : any) {
        message.error('请求失败，' + error.message);
      }
  }

  return (
    <PageContainer title="开发者平台" >
      {isComplete && (
        <>
          <Card  title={'开发者密钥（调用接口的凭证）'} style={{fontSize: '16px'}}>
            <p onClick={() => handleCopy('accessKey')} >
              AccessKey:  {data.accessKey}
              <SnippetsTwoTone style={{ fontSize: '16px', cursor: 'pointer' }} />
            </p>
            <p onClick={() => handleCopy('secretKey')}>
              SecretKey:  {data.secretKey}
              <SnippetsTwoTone style={{ fontSize: '16px', cursor: 'pointer' }} />
            </p>
            <Button type="primary" onClick={ChangeApi}>
              重新生成 API 密钥
            </Button>

          </Card>
          <p>
          </p>
          <Card title={'开发者 SDK'}>
          </Card>
        </>
        )}
    </PageContainer>
  );
};
export default UserAPI;
