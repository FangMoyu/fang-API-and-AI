export default [
  { path: '/', name: '介绍' , icon: 'smile' , component: './Index'},
  { path: '/interface_info_view', name: '接口总览' , icon: 'smile' , component: './InterfaceInfoView'},
  { path: '/interface_info/:id', name: '主页' , icon: 'smile' , component: './InterfaceInfo', hideInMenu: true},
  { path: '/random_love_story', name: '随机土味情话' , icon: 'HeartOutlined' , component: './GenLoveStory'},


  { path: '/user_api' , icon: 'AppstoreAddOutlined' , component: './ApiOpen'},
  // Ai 功能路由

  {
    path: '/ai',
    name: 'AI 相关',
    icon: 'BarChartOutlined',
    routes: [
      {name: "智能分析(消息队列异步)", path: "/ai/add_chart_async_byMQ" , icon: "BarChartOutlined", component: "./AddChartAsyncByMQ"},
      {name: "我的图表(智能分析)", path: "/ai/my_chart" , icon: "AreaChartOutlined", component: "./MyChart"},
      {name: "AI 生成文案", path: "/ai/gen_copy_writing" , icon: "EditOutlined", component: "./GenCopyWriting"},
    ],
  },

  {
    path: '/user',
    layout: false,
    routes: [
      { name: '登录', path: '/user/login', component: './User/Login' },
      { name: '注册', path: '/user/register', component: './User/Register',executeInitialState: false },

    ],

  },
  {
    path: '/admin',
    name: '管理页',
    icon: 'crown',
    access: 'canAdmin',
    routes: [

      { name: '接口管理', icon: 'table', path: '/admin/interface_info', component: './Admin/InterfaceInfo' },
      { name: '接口监控', icon: 'table', path: '/admin/interface_info_analysis', component: './Admin/InterfaceInfoAnalysis' }
    ],
  },
  { path: '*', layout: false, component: './404' },
];
