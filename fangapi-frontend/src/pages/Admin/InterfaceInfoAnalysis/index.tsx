import React, {useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-components";
import ReactECharts from 'echarts-for-react';
import {listTopInterfaceInfoUsingGet} from "@/services/fangapi-backend/analysisController";
const InterfaceInfoAnalysis: React.FC = () => {
  const [data, setData] = useState<API.InterfaceInfoVO[]>([]);

  useEffect(() => {
    try {
      listTopInterfaceInfoUsingGet({
        limit: 3
      }).then(res => {
        if (res.data) {
          setData(res.data);
        }
      })
    } catch (e: any) {
      console.error("获取最大接口信息失败")
    }
    // todo 从远程获取数据
  }, [])

  // 映射：{ value: 1048, name: 'Search Engine' },
  const chartData = data.map(item => {
    return {
      value: item.totalNum,
      name: item.description,
    }
  })


  const option = {
    title: {
      text: '调用次数最多的接口TOP3',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: '50%',
        data: chartData,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  };


  return (
    <PageContainer>
      <ReactECharts option={option} />
    </PageContainer>
  );
};
export default InterfaceInfoAnalysis;
