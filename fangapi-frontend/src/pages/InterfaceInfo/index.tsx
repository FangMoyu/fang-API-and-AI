import {
  getInterfaceInfoByIdUsingGet,
  invokeInterfaceInfoUsingPost
} from '@/services/fangapi-backend/interfaceInfoController';
import { PageContainer } from '@ant-design/pro-components';
import { Button, Card, Descriptions, Form, Input, message } from 'antd';
import TextArea from 'antd/lib/input/TextArea';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';

/**
 * 主页
 * @constructor
 */
const Index: React.FC = () => {
  // 定义状态和钩子函数
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState<API.InterfaceInfo>();
  // 使用 useParams 钩子函数获取动态路由参数
  const params = useParams();
  // 存储接口调用后的结果变量
  const [invokeRes, setInvokeRes] = useState<any>();
  // 调用加载状态变量，默认为 false
  const [invokeLoading, setInvokeLoading] = useState(false);

  const loadData = async () => {
    // 检查动态路由参数是否存在
    if (!params.id) {
      message.error('参数不存在');
      return;
    }
    setLoading(true);
    try {
      // 发起请求获取接口信息，接受一个包含 id 参数的对象作为参数
      const res = await getInterfaceInfoByIdUsingGet({
        id: Number(params.id),
      });
      // 将获取到的接口信息设置到 data 状态中
      setData(res.data);
    } catch (error: any) {
      // 请求失败处理
      message.error('请求失败，' + error.message);
    }
    // 请求完成，设置 loading 状态为 false，表示请求结束，可以停止加载状态的显示
    setLoading(false);
  };

  useEffect(() => {
    loadData();
  }, []);

  const onFinish = async (values: any) => {
    // 如果接口地址的 id 不存在，则直接返回
    if(!params){
      message.error("接口不存在");
      return;
    }
    // 在开始调用接口方法时，将 invokeLoading 设置为 true ， 表示正在加载中
    try{
     const res =  await invokeInterfaceInfoUsingPost({
        //请求的接口 id
        id: params.id,
        //请求参数
       ...values
      })
      setInvokeRes(res.data);
    }catch (error : any){
      message.error("操作失败" + error.message)
    }
    // 无论成功还是失败的请求，都要将 invokeLoading 设置为 false，表示加载完成
    setInvokeLoading(false);
  };

  return (
    <PageContainer title="查看接口文档">
      <Card>
        {data ? (
          <Descriptions title={data.name}>
            <Descriptions.Item label="接口状态">{data.status}</Descriptions.Item>
            <Descriptions.Item label="描述">{data.description}</Descriptions.Item>
            <Descriptions.Item label="请求地址">{data.url}</Descriptions.Item>
            <Descriptions.Item label="请求参数">{data.requestParams}</Descriptions.Item>
            <Descriptions.Item label="请求方法">{data.method}</Descriptions.Item>
            <Descriptions.Item label="请求头">{data.requestHeader}</Descriptions.Item>
            <Descriptions.Item label="响应头">{data.responseHeader}</Descriptions.Item>
            <Descriptions.Item label="创建时间">{data.createTime}</Descriptions.Item>
            <Descriptions.Item label="更新时间">{data.updateTime}</Descriptions.Item>
          </Descriptions>
        ) : (
          <>接口不存在</>
        )}
      </Card>
      <Card>
        <Form name="invoke" layout="vertical" onFinish={onFinish}>
          <Form.Item label="请求参数" name="userRequestParams">
            <Input.TextArea />
          </Form.Item>
          <Form.Item wrapperCol={{ span: 16 }}>
            <Button type="primary" htmlType="submit">
              调用
            </Button>
          </Form.Item>
        </Form>
      </Card>
      {/* 显示调用接口的结果 */}
      <Card title="返回结果" loading={invokeLoading}>
        {invokeRes}
      </Card>
    </PageContainer>
  );
};

export default Index;
