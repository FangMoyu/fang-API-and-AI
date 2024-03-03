import { PageContainer } from '@ant-design/pro-components';
import { useModel } from '@umijs/max';
import { Card, theme } from 'antd';
import React from 'react';

/**
 * 每个单独的卡片，为了复用样式抽成了组件
 * @param param0
 * @returns
 */
const InfoCard: React.FC<{
  title: string;
  index: number;
  desc: string;
  href: string;
}> = ({ title, href, index, desc }) => {
  const { useToken } = theme;

  const { token } = useToken();

  return (
    <div
      style={{
        backgroundColor: token.colorBgContainer,
        boxShadow: token.boxShadow,
        borderRadius: '8px',
        fontSize: '14px',
        color: token.colorTextSecondary,
        lineHeight: '22px',
        padding: '16px 19px',
        minWidth: '220px',
        flex: 1,
      }}
    >
      <div
        style={{
          display: 'flex',
          gap: '4px',
          alignItems: 'center',
        }}
      >
        <div
          style={{
            width: 48,
            height: 48,
            lineHeight: '22px',
            backgroundSize: '100%',
            textAlign: 'center',
            padding: '8px 16px 16px 12px',
            color: '#FFF',
            fontWeight: 'bold',
            backgroundImage:
              "url('https://gw.alipayobjects.com/zos/bmw-prod/daaf8d50-8e6d-4251-905d-676a24ddfa12.svg')",
          }}
        >
          {index}
        </div>
        <div
          style={{
            fontSize: '16px',
            color: token.colorText,
            paddingBottom: 8,
          }}
        >
          {title}
        </div>
      </div>
      <div
        style={{
          fontSize: '14px',
          color: token.colorTextSecondary,
          textAlign: 'justify',
          lineHeight: '22px',
          marginBottom: 8,
        }}
      >
        {desc}
      </div>
      <a href={href} target="_self" rel="noreferrer">
        了解更多 {'>'}
      </a>
    </div>
  );
};

const Welcome: React.FC = () => {
  const { token } = theme.useToken();
  const { initialState } = useModel('@@initialState');
  return (
    <PageContainer>
      <Card
        style={{
          borderRadius: 8,
        }}
        bodyStyle={{
          backgroundImage:
            initialState?.settings?.navTheme === 'realDark'
              ? 'background-image: linear-gradient(75deg, #1A1B1F 0%, #191C1F 100%)'
              : 'background-image: linear-gradient(75deg, #FBFDFF 0%, #F5F7FF 100%)',
        }}
      >
        <div
          style={{
            backgroundPosition: '100% -30%',
            backgroundRepeat: 'no-repeat',
            backgroundSize: '274px auto',
            backgroundImage:
              "url('https://gw.alipayobjects.com/mdn/rms_a9745b/afts/img/A*BuFmQqsB2iAAAAAAAAAAAAAAARQnAQ')",
          }}
        >
          <div
            style={{
              fontSize: '20px',
              color: token.colorTextHeading,
            }}
          >
            老方的综合接口调用平台
          </div>
          <p
            style={{
              fontSize: '14px',
              color: token.colorTextSecondary,
              lineHeight: '22px',
              marginTop: 16,
              marginBottom: 32,
              width: '65%',
            }}
          >
            欢迎大家访问我的聚合平台❤❤❤，<span style={{color : "black" , fontWeight: 'bold'}}> 目前本站仍处于开发状态，众多功能还未上线，请大家耐心等待后续功能的上线😊。 </span>
            (PS: 老方正在努力加班中ing 😫😫😫)
            <span style={{color : 'black', fontWeight: 'bold'}}> 本网站集成了 AI、三方接口，支持 AI 对话、生成文章、普通接口调用，还有关注、私信等功能 ✨✨ </span>

            。注册账号后可以免费得到 20 次调用接口的机会，大家可以在本站使用 AI 进行智能对话和文章生成，让您的工作更加顺畅和高效。而且本站支持第三方接口调用，

            如果您有自己的接口，可以根据我们的 API 规则提供接口，一旦管理员审核通过，您就可以在本站上线自己的专属接口 😍😍 实现更多可能性！💬💡

            此外，网站还提供了关注和私信功能，让您可以和其他用户互动、交流，建立更紧密的联系。无论是学习、工作还是娱乐，希望本站能够让您的生活变得更加便利和有趣！💌👯

            快来体验这个综合接口网站吧，开启全新的互联网时代，让智能技术为您带来更多惊喜和乐趣！记得分享给更多小伙伴，一起来探索这个神奇的世界吧！🚀🌈
          </p>
          <div
            style={{
              display: 'flex',
              flexWrap: 'wrap',
              gap: 16,
            }}
          >
            <InfoCard
              index={1}
              href='/add_chart_async_byMQ'
              title="了解 AI 智能生成"
              desc="本站提供了 AI 自动生成 BI 报表、自动生成文章、而且可以轻松地与 AI 直接进行对话"
            />
            <InfoCard
              index={2}
              title="了解 开发者 SDK "
              href="https://ant.design"
              desc="本站提供了供开发者直接使用的 SDK ,开发者可以通过 SDK 调用本站的大部分接口"
            />
            <InfoCard
              index={3}
              title="了解 接口"
              href="https://procomponents.ant.design"
              desc="本站提供了免费接口以及付费接口，付费接口调用需要消耗方块石，方块石可通过本站不定期活动和充值获得。"
            />
          </div>
        </div>
      </Card>
    </PageContainer>
  );
};

export default Welcome;
