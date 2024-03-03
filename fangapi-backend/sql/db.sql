use fangbi;
-- 接口信息表
create table if not exists fangbi.`interface_info`
(
    `id`             bigint                             not null auto_increment comment '主键' primary key,
    `name`           varchar(256)                       not null comment '名称',
    `description`    varchar(512)                       null comment '用户名',
    `url`            varchar(512)                       not null comment '接口地址',
    `requestHeader`  text                               null comment '请求头',
    `responseHeader` text                               null comment '响应头',
    `status`         int      default 0                 not null comment '接口类型',
    `requestParams`  text                               comment '请求参数',
    `method`         varchar(256)                       not null comment '请求方法类型',
    `userId`         bigint                             not null comment '创建人',
    `createTime`     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime`     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete`       tinyint  default 0                 not null comment '是否删除'
) comment '接口信息表';

insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('夏昊天', 'xpQh', 'www.james-collins.name', '龚峻熙', '苏晓啸', 0, '秦炎彬', 26585);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('田靖琪', 'Ab', 'www.felica-dicki.info', '于文博', '熊荣轩', 0, '潘正豪', 5915499);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('马昊焱', 'Vt', 'www.rudy-jacobi.org', '杨鸿煊', '郑远航', 0, '任伟宸', 48232005);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('沈文', 'Cyb', 'www.bailey-zboncak.biz', '黎凯瑞', '毛健柏', 0, '秦修杰', 22);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('龚立轩', 'pX', 'www.lila-rath.org', '程正豪', '曾乐驹', 0, '姜远航', 304895);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('徐雨泽', 'CRe6u', 'www.minna-kris.info', '彭晓博', '雷展鹏', 0, '陈懿轩', 14307799);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('郑越泽', 'GU', 'www.lizzie-cummings.info', '李文博', '刘志泽', 0, '曹鹤轩', 24);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('胡皓轩', 'GFIrL', 'www.milda-hane.com', '龙果', '袁雨泽', 0, '魏乐驹', 2711285637);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('郭鹏涛', 'rynMq', 'www.dustin-stroman.net', '唐思源', '袁文博', 0, '曾彬', 14633);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('卢鹏', 'JrURS', 'www.walter-kiehn.io', '龚立轩', '冯彬', 0, '孟志强', 944758);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('黎立轩', 'OgkD', 'www.caprice-zulauf.com', '阎懿轩', '曾思源', 0, '邹潇然', 7523251045);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('邱振家', 'JgcOQ', 'www.enoch-buckridge.biz', '覃俊驰', '梁雨泽', 0, '方文轩', 2);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('杜睿渊', 'TkeF', 'www.leon-hickle.io', '贾炫明', '韩思', 0, '于果', 0);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('孔涛', 'n3', 'www.archie-murray.com', '汪雨泽', '覃思', 0, '彭擎宇', 5786099317);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('刘健雄', '5Zzs', 'www.adriene-becker.biz', '陈明杰', '段明轩', 0, '陈雨泽', 91);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('邵远航', 'w7Fwi', 'www.heike-gottlieb.co', '沈智辉', '邹鹏', 0, '傅文博', 12670);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('潘锦程', 'qVrkY', 'www.michael-yundt.name', '严胤祥', '罗雪松', 0, '孟立果', 4);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('任智辉', 'EmnOx', 'www.elisa-okuneva.net', '田凯瑞', '韩伟祺', 0, '石明辉', 3);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('朱健雄', 'G2FH', 'www.giuseppe-satterfield.co', '夏鹏飞', '傅驰', 0, '范越彬', 4286);
insert into fangapi.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`,
                                      `method`, `userId`)
values ('廖晓啸', 'IJ', 'www.domingo-rutherford.biz', '潘思远', '黎健雄', 0, '邹思源', 567);


-- 用户调用接口关系表
create table if not exists fangbi.`user_interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `userId` bigint not null comment '调用用户 id',
    `interfaceInfoId` bigint not null comment '接口 id',
    `totalNum` int default 0 not null comment '总调用次数',
    `leftNum` int default 0 not null comment '剩余调用次数',
    `status` int default 0 not null comment '0-正常，1-禁用',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '用户调用接口关系';