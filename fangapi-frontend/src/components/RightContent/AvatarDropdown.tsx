import { LogoutOutlined, SettingOutlined, UserOutlined } from '@ant-design/icons';
import { history, useModel } from '@umijs/max';
import { Spin } from 'antd';
import { createStyles } from 'antd-style';
import type { MenuInfo } from 'rc-menu/lib/interface';
import React, { useCallback } from 'react';
import { flushSync } from 'react-dom';
import HeaderDropdown from '../HeaderDropdown';
import {userLogoutUsingPost} from "@/services/fangapi-backend/userController";
import {undefined} from "@umijs/utils/compiled/zod";

export type GlobalHeaderRightProps = {
  menu?: boolean;
  children?: React.ReactNode;
};

export const AvatarName = () => {
  const { initialState } = useModel('@@initialState');
  const { loginUser } = initialState || {};
  return <span className="anticon">{loginUser?.userName}</span>;
};

const useStyles = createStyles(({ token }) => {
  return {
    action: {
      display: 'flex',
      height: '48px',
      marginLeft: 'auto',
      overflow: 'hidden',
      alignItems: 'center',
      padding: '0 8px',
      cursor: 'pointer',
      borderRadius: token.borderRadius,
      '&:hover': {
        backgroundColor: token.colorBgTextHover,
      },
    },
  };
});

export const AvatarDropdown: React.FC<GlobalHeaderRightProps> = ({ menu, children }) => {
  /**
   * 退出登录，并且将当前的 url 保存
   */
  const { styles } = useStyles();

  const { initialState, setInitialState } = useModel('@@initialState');

  const onMenuClick =useCallback(
    async (event: MenuInfo) => {
      const { key } = event;
      if (key === 'logout') {
        flushSync(() => {
          // @ts-ignore
          setInitialState((s) => ({ ...s, loginUser: undefined }));
        });
        const res = await userLogoutUsingPost();
        // @ts-ignore
        if(res.data){
          //登录状态要清除,不太优雅的写法，但我不知道咋写，凑合能跑
          console.log("退出成功");
          const{ search, pathname} = window.location;
          const redirect = pathname + search;
          history.replace("/user/login",{redirect});
          return;
        }
      }else if(key === 'APICenter') {
        history.push('/user_api');
      }
      // history.push(`/account/${key}`);
    },
    [setInitialState],
  );

  const loading = (
    <span className={styles.action}>
      <Spin
        size="small"
        style={{
          marginLeft: 8,
          marginRight: 8,
        }}
      />
    </span>
  );

  if (!initialState) {
    return loading;
  }

  const { loginUser } = initialState;

  if (!loginUser || !loginUser.userName) {
    return loading;
  }

  const menuItems = [
    {
        key: 'APICenter',
        icon: <UserOutlined />,
        label: '开发者平台',
    },

    {
        type: 'divider' as const,
    },
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: '退出登录',
    },
  ];

  return (
    <HeaderDropdown
      menu={{
        selectedKeys: [],
        onClick: onMenuClick,
        items: menuItems,
      }}
    >
      {children}
    </HeaderDropdown>
  );
};
