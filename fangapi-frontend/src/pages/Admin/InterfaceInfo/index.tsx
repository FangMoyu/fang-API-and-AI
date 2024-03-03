import { removeRule } from '@/services/ant-design-pro/api';
import { PlusOutlined } from '@ant-design/icons';
import type { ActionType, ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import {
  FooterToolbar,
  PageContainer,
  ProDescriptions,
  ProTable,
} from '@ant-design/pro-components';
import '@umijs/max';
import { Button, Drawer, message } from 'antd';
import React, { useRef, useState } from 'react';

import {
  addinterfaceInfoUsingPost,
  deleteinterfaceInfoUsingPost,
  listinterfaceInfoByPageUsingGet,
  offlineInterfaceInfoUsingPost,
  onlineInterfaceInfoUsingPost,
  updateinterfaceInfoUsingPost
} from "@/services/fangapi-backend/interfaceInfoController";
import {SortOrder} from "antd/lib/table/interface";
import CreateModal from "@/pages/Admin/InterfaceInfo/components/CreateModal";
import UpdateModal from "@/pages/Admin/InterfaceInfo/components/UpdateModal";

/**
 * @en-US Add node
 * @zh-CN 添加节点
 * @param fields
 */
const handleAdd = async (fields: API.InterfaceInfo) => {
  const hide = message.loading('正在添加');
  try {
    await addinterfaceInfoUsingPost({
      ...fields,
    });
    hide();
    message.success('Added successfully');
    return true;
  } catch (error) {
    hide();
    message.error('Adding failed, please try again!');
    return false;
  }
};



const TableList: React.FC = () => {
  /**
   * @en-US Pop-up window of new window
   * @zh-CN 新建窗口的弹窗
   *  */
  const [createModalOpen, handleModalOpen] = useState<boolean>(false);
  /**
   * @en-US The pop-up window of the distribution update window
   * @zh-CN 分布更新窗口的弹窗
   * */
  const [updateModalOpen, handleUpdateModalOpen] = useState<boolean>(false);
  const [showDetail, setShowDetail] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.InterfaceInfo>();
  const [selectedRowsState, setSelectedRows] = useState<API.InterfaceInfo[]>([]);

  /**
   *  Delete node
   * @zh-CN 删除节点
   *
   * @param fields
   */
  const handleRemove = async (record: API.InterfaceInfo) => {
    const hide = message.loading('正在删除');
    try {
        hide();
        const res = await deleteinterfaceInfoUsingPost({
          id: record.id,
        });
        if(res.code === 0) {
          message.success('接口信息删除成功');
            if(actionRef.current){
              actionRef.current.reload();
            }
        }
    } catch (error) {
      hide();
      message.error('接口信息删除失败');
    }
  };

  /**
   *  Delete node
   * @zh-CN 上线
   *
   * @param record
   */
  const handleOnline = async (record: API.InterfaceInfo) => {
    const hide = message.loading('正在上线');
    try {
      hide();
      const res = await onlineInterfaceInfoUsingPost({
        id: record.id,
      });
      if(res.code === 0) {
        message.success('操作成功');
        if(actionRef.current){
          actionRef.current.reload();
        }
      }
    } catch (error) {
      hide();
      message.error('接口上线失败');
    }
  };
  /**
   *  Delete node
   * @zh-CN 上线
   *
   * @param record
   */
  const handleOffline = async (record: API.InterfaceInfo) => {
    const hide = message.loading('下线中');
    try {
      hide();
      const res = await offlineInterfaceInfoUsingPost({
        id: record.id,
      });
      if(res.code === 0) {
        message.success('操作成功');
        if(actionRef.current){
          actionRef.current.reload();
        }
      }
    } catch (error) {
      hide();
      message.error('接口下线失败');
    }
  };

  /**
   * @en-US Update node
   * @zh-CN 更新节点
   *
   * @param fields
   */
  const handleUpdate = async (fields: API.InterfaceInfo) => {
    //如果没有选中当前行，则直接返回
    if(!currentRow){
      return;
    }
    const hide = message.loading('Configuring');
    try {
      await updateinterfaceInfoUsingPost({
        id: currentRow.id,
        //把整个对象作为参数
        ...fields
      });
      hide();
      message.success('Configuration is successful');
      return true;
    } catch (error) {
      hide();
      message.error('Configuration failed, please try again!');
      return false;
    }
  };



  /**
   * @en-US International configuration
   * @zh-CN 国际化配置
   * */

  const columns: ProColumns<API.InterfaceInfo>[] = [
    {
      title: 'id',
      dataIndex: 'id',
      valueType: 'index',
    },
    {
      title: '接口名称',
      //name对应后端的字段名
      dataIndex: 'name',
      // tip不用管，一个规则
      // tip: 'The rule name is the unique key',

      // render不用管，它是说渲染类型，默认我们渲染类型就是text
      // render: (dom, entity) => {
      //   return (
      //     <a
      //       onClick={() => {
      //         setCurrentRow(entity);
      //         setShowDetail(true);
      //       }}
      //     >
      //       {dom}
      //     </a>
      //   );
      // },

      // 展示文本
      valueType: 'text',
      formItemProps:{
        rules:[{
          required: true,
        }]
      },

    },
    {
      title: '描述',
      //description对应后端的字段名
      dataIndex: 'description',
      // 展示的文本为富文本编辑器
      valueType: 'textarea',
    },
    {
      title: '请求方法',
      dataIndex: 'method',
      // 展示的文本为富文本编辑器
      valueType: 'text',
      formItemProps:{
        required:true
      }
    },
    {
      title: 'url',
      dataIndex: 'url',
      valueType: 'text',
      formItemProps:{
        required:true
      }
    },
    {
      title: '请求参数',
      dataIndex: 'requestParams',
      valueType: 'jsonCode',
    },
    {
      title: '请求头',
      dataIndex: 'requestHeader',
      valueType: 'jsonCode',
    },
    {
      title: '响应头',
      dataIndex: 'responseHeader',
      valueType: 'jsonCode',
    },
    {
      title: '状态',
      dataIndex: 'status',
      hideInForm: true,
      valueEnum: {
        0: {
          text: '关闭',
          status: 'Default',
        },
        1: {
          text: '开启',
          status: 'Processing',
        },
      },
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      valueType: 'dateTime',
      hideInForm: true,
    },
    {
      title: '更新时间',
      dataIndex: 'updateTime',
      valueType: 'dateTime',
      hideInForm: true,
    },


    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <a
          key="config"
          onClick={() => {
            handleUpdateModalOpen(true);
            setCurrentRow(record);
          }}
        >
          修改
        </a>,
        //三元运算符，判断是否要显示
        record.status === 0 ? <a
          key="online"
          onClick={ async () => {
            handleOnline(record);
          }}
        >
          上线
        </a> : null,

        //三元运算符，判断是否要显示
        record.status ===1 ?
          <Button
          type="text"
          danger
          key="offline"
          onClick={ async () => {
            handleOffline(record);
          }}
        >
          下线
        </Button> : null,


        <Button
          type="text"
          danger
          key="delete"
          onClick={ async () => {
            handleRemove(record);
          }}
        >
          删除
        </Button>,

        <a key="subscribeAlert" href="https://procomponents.ant.design/">
        </a>,
      ],
    },
  ];
  // @ts-ignore
  // @ts-ignore
  return (
    <PageContainer>
      <ProTable<API.RuleListItem, API.PageParams>
        headerTitle={'查询表格'}
        actionRef={actionRef}
        rowKey="key"
        search={{
          labelWidth: 120,
        }}
        scroll={{x : 100}}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              handleModalOpen(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
        request={async (params, sort: Record<string, SortOrder>, filter: Record<string, (string | number)[] | null>) => {
              const res = await listinterfaceInfoByPageUsingGet({
                ...params
              })
              if(res?.data){
                return {
                  data: res?.data.records || [],
                  success: true,
                  total: res.data.total,
                }
          }else {
                return {
                  data: [],
                  success: false,
                  total: 0,
                }
              }
        }}
        columns={columns}
        rowSelection={{
          onChange: (_, selectedRows) => {
            setSelectedRows(selectedRows);
          },
        }}
      />
      {selectedRowsState?.length > 0 && (
        <FooterToolbar
          extra={
            <div>
              已选择{' '}
              <a
                style={{
                  fontWeight: 600,
                }}
              >
                {selectedRowsState.length}
              </a>{' '}
              项 &nbsp;&nbsp;
              <span>
                服务调用次数总计 {selectedRowsState.reduce((pre, item) => pre + item.callNo!, 0)} 万
              </span>
            </div>
          }
        >
          <Button
            onClick={async () => {
              await handleRemove(selectedRowsState);
              setSelectedRows([]);
              actionRef.current?.reloadAndRest?.();
            }}
          >
            批量删除
          </Button>
          <Button type="primary">批量审批</Button>
        </FooterToolbar>
      )}
      {/* 创建一个CreateModal组件，用于在点击新增按钮时弹出 */}
      <CreateModal
        columns={columns}
        // 当取消按钮被点击时,设置更新模态框为false以隐藏模态窗口
        onCancel={() => {
          handleModalOpen(false);
        }}
        // 当用户点击提交按钮之后，调用handleAdd函数处理提交的数据，去请求后端添加数据(这里的报错不用管,可能里面组件的属性和外层的不一致)
        onSubmit={(values) => {
          handleAdd(values);
        }}
        // 根据更新窗口的值决定模态窗口是否显示
        visible={createModalOpen}
      />

      <UpdateModal
        values={currentRow || {}}
        columns={columns}
        onCancel={() =>{
          handleUpdateModalOpen(false);
          if(!showDetail){
            setCurrentRow(undefined);
          }
        }}
        onSubmit={async value =>{
          const success = await handleUpdate(value);
          if(success){
            handleUpdateModalOpen(false);
            setCurrentRow(undefined);
            if(actionRef.current){
              actionRef.current.reload();
            }
        }}
      }
        visible={updateModalOpen}
      />
      <Drawer
        width={600}
        open={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.name && (
          <ProDescriptions<API.RuleListItem>
            column={2}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.name,
            }}
            columns={columns as ProDescriptionsItemProps<API.RuleListItem>[]}
          />
        )}
      </Drawer>
    </PageContainer>
  );
};
export default TableList;
