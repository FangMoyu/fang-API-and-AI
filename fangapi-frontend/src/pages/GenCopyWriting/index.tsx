import {Button, Card, Form, Image, message, Space} from 'antd';
import TextArea from 'antd/es/input/TextArea';
import React, {  useState } from 'react';
import {useForm} from "antd/es/form/Form";
import {
  invokeAiGenCopyWritingUsingPost
} from "@/services/fangapi-backend/genAiController";
import Meta from "antd/es/card/Meta";


const GenCopyWriting: React.FC = () => {

  // 用来判断用户是否提交了请求
const[submitting, setSubmitting] = useState<boolean>(false);
const[form] = useForm();
const[copyWriting, setCopyWriting] = useState<API.CopyWritingResponse>();
  const onFinish = async (values: any) => {
    // 如果已经是提交中的状态，直接返回，避免重复提交
    if(submitting){
      return;
    }
    setSubmitting(true);
    // 第一个参数需要一个完整对象
    const params = {
      ...values,
    }
    // 将请求发送包裹在一个 try - catch 块中，当请求发生异常时，给用户一个默认的响应
    try{
      // 发送请求
      const res = await invokeAiGenCopyWritingUsingPost(params);
      if(!res?.data){
        console.log(res);
        message.error("文案生成失败");
      }else {
        message.success("文案生成成功");
        const jsonCopyWriting = JSON.parse(res.data);
        setCopyWriting(jsonCopyWriting);
      }

    } catch (e: any){
      message.error("文案生成失败:" + e.message);
    }
    setSubmitting(false);
  }


  return (
    // 把页面内容指定一个类名add-chart-async
    <div className="gen_copy_writing">
          <Card title={"AI 文案生成"} size={'default'}>
            <Form
              form={form}
              // 表单名称改为addChart
              name="addChart"
              labelAlign={"left"}
              wrapperCol={{span: 16}}
              onFinish={onFinish}
              // 初始化数据啥都不填，为空
              initialValues={{  }}
            >

              <Form.Item name="userInput" label="文案需求"  rules={[{ required: true, message: '需求不能为空！' }]}>
                <TextArea placeholder={"请输入你的文案需求，例如：请帮我写一份工作报告"}/>
              </Form.Item>

              <Form.Item wrapperCol={{ span: 12, offset: 6 }}>
                <Space>
                  <Button type="primary" htmlType="submit" loading={submitting}>
                    提交
                  </Button>
                  <Button htmlType="reset">重置</Button>
                </Space>
              </Form.Item>
            </Form>
          </Card>
      <br/>
      <br/>

      <div>
        {copyWriting && (
          <>
            <Card title={"文案生成结果"} bordered={false}>
              <Meta>

              </Meta>
              <div>
                {copyWriting.genCopyWriting}
              </div>
            </Card>
          </>
        )}
      </div>
    </div>

  );
};
export default GenCopyWriting;
