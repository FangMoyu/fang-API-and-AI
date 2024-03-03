import { PageContainer } from '@ant-design/pro-components';
import React, {useEffect, useState} from 'react';
import {Card, List, message} from "antd";
import {listinterfaceInfoByPageUsingGet} from "@/services/fangapi-backend/interfaceInfoController";
import Meta from "antd/es/card/Meta";
import {SettingOutlined} from "@ant-design/icons";
import {Link} from "@umijs/max";

/**
 * 接口页
 */
const Index: React.FC = () =>{

  // 加载状态
  const [loading, setLoading] = useState(false);
  // 列表数据
  const [list, setList] = useState<API.InterfaceInfo[]>([]);
  // 总数
  const [total ,setTotal] = useState<number>(0);

  const loadData = async (current = 1, pageSize = 5) =>{
    //开始加载数据，将状态设置成加载中
    setLoading(true);
    try{
      const res = await listinterfaceInfoByPageUsingGet({
        current,
        pageSize
      });
      // 将请求返回的数据设置到列表数据状态中
      setList(res?.data?.records ?? []);
      // 将请求返回的总数设置到总数状态中
      setTotal(res?.data?.total ?? 0);
    }catch (error : any){
      message.error("请求失败", error.message);
    }
    setLoading(false);
  };
  //页面加载完成就执行加载数据的函数
  useEffect( () =>{
    loadData();
  } , []);
  const cardsData = [
    { title: '智能分析', description: '提供 Excel 文件 AI 自动生成图表', imageUrl: 'https://fang-images.oss-cn-fuzhou.aliyuncs.com/images/IMG_2988.PNG' ,clickUrl: "/ai/add_chart_async_byMQ" },
    { title: '文案生成', description: '向 AI 提问来自动生成文案', imageUrl: 'https://gd-hbimg.huaban.com/dbbd76aa3ac7a8e399fcdb169b4fe7c0458b45ab196d7-Hladqz' ,clickUrl: "/ai/gen_copy_writing" },
    { title: '随机土味情话', description: '随机生成土味情话', imageUrl: 'https://ts1.cn.mm.bing.net/th/id/R-C.95169f1fe9bf9f8c0142e3593a71c7ec?rik=oZ6dVbyEKJrHow&riu=http%3a%2f%2fwww.kuaipng.com%2fUploads%2fpic%2fwater%2f10121%2fgoods_water_10121_698_496.05_.png&ehk=GOacBRL2S0yuSfP1o%2bCBJJalpVBIBn32UnL1g9xG1uA%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1' ,clickUrl: "/" },
    // 可以继续添加更多 Card 数据
  ];
  return(
  <PageContainer title="可用接口">
    <div style={{ display: 'flex',height: 600, flexWrap: 'wrap' }}>
      {cardsData.map((card, index) => (
        <Link to={card.clickUrl} key={index} style={{ marginRight: 60 }}>
        <Card key={index} hoverable style={{ width: 240 , marginRight: 30, marginBottom: 20 ,height: 300}}
              cover={<img alt="图像加载失败" src={card.imageUrl} style={{height: 200, objectFit: 'cover'}} />}>
          <Meta title={card.title} description={card.description} />
        </Card>
        </Link>
      ))}
    </div>
  </PageContainer>
  );
};



export default Index;
