import {Button, Card,  Form, Input, message,  Select, Space, Upload} from 'antd';
import TextArea from 'antd/es/input/TextArea';
import React, {  useState } from 'react';
import {UploadOutlined} from "@ant-design/icons";
import {useForm} from "antd/es/form/Form";
import {invokeAiGenChartInterfaceUsingPost} from "@/services/fangapi-backend/genAiController";

const AddChart: React.FC = () => {

  // 用来判断用户是否提交了请求
const[submitting, setSubmitting] = useState<boolean>(false);
const[form] = useForm();
  const onFinish = async (values: any) => {
    // 如果已经是提交中的状态，直接返回，避免重复提交
    if(submitting){
      return;
    }
    setSubmitting(true);
    // 第一个参数需要一个完整对象
    const params = {
      ...values,
      file:undefined
    }
    // 将请求发送包裹在一个 try - catch 块中，当请求发生异常时，给用户一个默认的响应
    try{
      // 发送请求
      const res = await
        invokeAiGenChartInterfaceUsingPost(params,{},values.file.file.originFileObj);
      if(!res?.data){
        message.error("分析失败");
      }else{
        message.success("分析任务提交成功，稍后请在我的图表页面查看");
        form.resetFields();
      }
    } catch (e: any){
      message.error("分析失败:" + e.message);
    }
    setSubmitting(false);
  }

  return (
    // 把页面内容指定一个类名add-chart-async
    <div className="add-chart-async">
          <Card title={"智能分析"}>
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
              <Form.Item name="goal" label="分析目标"  rules={[{ required: true, message: '分析目标不能为空！' }]}>
                <TextArea placeholder={"请输入你的分析需求，比如：分析网站用户的增长情况"}/>
              </Form.Item>
              <Form.Item
                name="name"
                label="图表名称">
                <Input placeholder="请输入图表名称" />
              </Form.Item>

              <Form.Item
                name="chartType"
                label="图表类型"
              >
                <Select
                  options ={[
                    { value: '折线图', label: '折线图' },
                    { value: '柱状图', label: '柱状图' },
                    { value: '堆叠图', label: '堆叠图' },
                    { value: '饼图', label: '饼图' },
                    { value: '雷达图', label: '雷达图' },
                  ]}
                />
              </Form.Item>

              <Form.Item
                name="file"
                label="原始数据"
              >
                <Upload name="file">
                  <Button icon={<UploadOutlined />}>上传 Excel 文件</Button>
                </Upload>
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
    </div>
  );
};
export default AddChart;
