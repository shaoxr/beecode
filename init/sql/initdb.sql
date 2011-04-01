-- 数据字典
insert into dict_view(version,key_name,key_value,key_desc) values(1,'ACT_STATUS','1','审核失败');
insert into dict_view(version,key_name,key_value,key_desc) values(1,'ACT_STATUS','2','已作废');
insert into dict_view(version,key_name,key_value,key_desc) values(1,'ACT_STATUS','0','待审核');
insert into dict_view(version,key_name,key_value,key_desc) values(1,'ACT_STATUS','3','待发放');
insert into dict_view(version,key_name,key_value,key_desc) values(1,'ACT_STATUS','4','已开放');
insert into dict_view(version,key_name,key_value,key_desc) values(1,'ACT_STATUS','5','已过期');
insert into dict_view(version,key_name,key_value,key_desc) values(1,'ACT_STATUS','6','已关闭');

insert into dict_view(version,key_name,key_value,key_desc) values(1,'COUPON_STATUS','0','无效');
insert into dict_view(version,key_name,key_value,key_desc) values(1,'COUPON_STATUS','1','有效');
insert into dict_view(version,key_name,key_value,key_desc) values(1,'COUPON_STATUS','2','过期');
insert into dict_view(version,key_name,key_value,key_desc) values(1,'COUPON_STATUS','3','挂失');
insert into dict_view(version,key_name,key_value,key_desc) values(1,'COUPON_STATUS','4','已用完');
insert into dict_view(version,key_name,key_value,key_desc) values(1,'COUPON_STATUS','5','已经发送');
insert into dict_view(version,key_name,key_value,key_desc) values(1,'COUPON_STATUS','6','发送成功');
insert into dict_view(version,key_name,key_value,key_desc) values(1,'COUPON_STATUS','7','发送失败');

insert into roles(role_name,role_no,version) values('系统管理员','ROLE_ADMIN',1);
insert into roles(role_name,role_no,version) values('业务员','ROLE_BUSINESS',1);
insert into roles(role_name,role_no,version) values('审核员','ROLE_CHECK',1);
insert into roles(role_name,role_no,version) values('系统查询','ROLE_QUERY',1);

insert into business_type(biz_name,biz_no,version) values('兑换券','00',1);
insert into business_type(biz_name,biz_no,version) values('折扣券','01',1);
-- 初始化序号种子
insert into hibernate_sequences(sequence_name, next_val) values("actNo",1);
insert into hibernate_sequences(sequence_name, next_val) values("operNo",100);
