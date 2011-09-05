# MySQL-Front 5.1  (Build 4.2)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: 127.0.0.1    Database: beecodedb
# ------------------------------------------------------
# Server version 5.1.50-community-log

CREATE DATABASE `beecodedb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `beecodedb`;

#
# Source for table coupon
#

CREATE TABLE `coupon` (
  `coupon_id` bigint(20) NOT NULL,
  `acct_mobile` varchar(16) DEFAULT NULL,
  `acct_name` varchar(10) DEFAULT NULL,
  `acct_no` varchar(24) DEFAULT NULL,
  `business_type` varchar(2) DEFAULT NULL,
  `check_code` varchar(16) DEFAULT NULL,
  `coupon_status` int(11) DEFAULT NULL,
  `gen_time` datetime DEFAULT NULL,
  `mms_desc` varchar(100) DEFAULT NULL,
  `mms_id` varchar(20) DEFAULT NULL,
  `mms_status` int(11) DEFAULT NULL,
  `rebate_rate` decimal(2,2) DEFAULT NULL,
  `remain_times` int(11) DEFAULT NULL,
  `serial_no` varchar(36) DEFAULT NULL,
  `sms_desc` varchar(100) DEFAULT NULL,
  `sms_status` int(11) DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `marketing_act` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `back_amount` decimal(10,2) DEFAULT NULL,
  `exchange_name` varchar(50) DEFAULT NULL,
  `exchange_amount` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`coupon_id`),
  KEY `FKAF42D826DDF44BC0` (`marketing_act`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table coupon
#

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (654218071500,'18650071122','邵信仁','4512893300082105','00',NULL,1,'2011-08-08 19:45:43',NULL,NULL,0,NULL,1,'dd926fae-b1bd-416c-8f2d-0e09a2732cb8',NULL,0,1,0,171,NULL,NULL,'10yuan4444',10);
INSERT INTO `coupon` VALUES (657219002102,'18650071122','邵信仁','4512893300082105','00',NULL,1,'2011-08-08 19:48:45',NULL,NULL,0,NULL,111,'8d5ec45e-09a5-4a95-a9e4-cd2f5f1413be',NULL,0,111,0,172,NULL,NULL,'1',0);
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table coupon_ctrl
#

CREATE TABLE `coupon_ctrl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batch_no` varchar(255) DEFAULT NULL,
  `business_type` varchar(2) NOT NULL,
  `check_date` datetime NOT NULL,
  `check_day` date DEFAULT NULL,
  `coupon_id` bigint(20) DEFAULT NULL,
  `device_no` varchar(16) NOT NULL,
  `encode_version` varchar(2) NOT NULL,
  `partner_no` varchar(32) NOT NULL,
  `rebate_rate` decimal(19,2) DEFAULT NULL,
  `serial_no` varchar(45) DEFAULT NULL,
  `trace_no` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `void_flag` varchar(255) DEFAULT NULL,
  `back_amount` decimal(10,2) DEFAULT NULL,
  `off_amount` decimal(10,2) DEFAULT NULL,
  `original_amount` decimal(10,2) DEFAULT NULL,
  `exchange_name` varchar(50) DEFAULT NULL,
  `exchange_amount` decimal(19,2) DEFAULT NULL,
  `acct_no` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

#
# Dumping data for table coupon_ctrl
#

LOCK TABLES `coupon_ctrl` WRITE;
/*!40000 ALTER TABLE `coupon_ctrl` DISABLE KEYS */;
/*!40000 ALTER TABLE `coupon_ctrl` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table customer
#

CREATE TABLE `customer` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `old_name` varchar(100) DEFAULT NULL,
  `new_name` varchar(20) DEFAULT NULL,
  `count` bigint(10) DEFAULT '0',
  `act_no` bigint(20) NOT NULL DEFAULT '0',
  `file_status` bigint(2) DEFAULT '0',
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=155 DEFAULT CHARSET=utf8;

#
# Dumping data for table customer
#

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (152,'template (6).xls','1701312803313843',1,170,0,0);
INSERT INTO `customer` VALUES (153,'template (6).xls','1711312803578890',1,171,1,1);
INSERT INTO `customer` VALUES (154,'template (6).xls','1721312804116687',1,172,1,1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table hibernate_sequences
#

CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table hibernate_sequences
#

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('actNo',1);
INSERT INTO `hibernate_sequences` VALUES ('operNo',100);
INSERT INTO `hibernate_sequences` VALUES ('marketing_act',173);
INSERT INTO `hibernate_sequences` VALUES ('coupon',220);
INSERT INTO `hibernate_sequences` VALUES ('oper',32);
INSERT INTO `hibernate_sequences` VALUES ('partner',27);
INSERT INTO `hibernate_sequences` VALUES ('customer',155);
INSERT INTO `hibernate_sequences` VALUES ('mms_send_list',14);
INSERT INTO `hibernate_sequences` VALUES ('resp_status',12015);
INSERT INTO `hibernate_sequences` VALUES ('send_list',69);
INSERT INTO `hibernate_sequences` VALUES ('partner_catalog',4);
INSERT INTO `hibernate_sequences` VALUES ('marketing_catalog',16);
INSERT INTO `hibernate_sequences` VALUES ('mms_template',85);
INSERT INTO `hibernate_sequences` VALUES ('terminal',86);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table marketing_act
#

CREATE TABLE `marketing_act` (
  `act_no` bigint(20) NOT NULL,
  `act_detail` varchar(100) NOT NULL,
  `act_name` varchar(30) NOT NULL,
  `act_status` int(11) DEFAULT NULL,
  `amount` decimal(19,2) DEFAULT NULL,
  `bind_card` varchar(2) NOT NULL,
  `biz_no` varchar(2) NOT NULL,
  `check_code` varchar(200) DEFAULT NULL,
  `coupon_sum` bigint(20) DEFAULT '0',
  `end_date` datetime NOT NULL,
  `file_name` varchar(1000) DEFAULT NULL,
  `gen_time` datetime DEFAULT NULL,
  `mms_content` varchar(400) DEFAULT NULL,
  `mms_send_sum` bigint(20) DEFAULT NULL,
  `mms_title` varchar(40) DEFAULT NULL,
  `oper_no` bigint(20) DEFAULT NULL,
  `rebate_rate` decimal(2,2) DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  `sms_send_sum` bigint(20) DEFAULT NULL,
  `start_date` datetime NOT NULL,
  `times` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `marketing_catalog` bigint(20) NOT NULL,
  `mms_sending` int(11) DEFAULT '0',
  `sms_sending` int(11) DEFAULT '0',
  `max_amount` decimal(19,2) DEFAULT NULL,
  `back_rate` decimal(19,2) DEFAULT NULL,
  `exchange_name` varchar(100) DEFAULT NULL,
  `mms_template_id` bigint(20) DEFAULT NULL,
  `import_type` int(2) DEFAULT NULL,
  `suffix` varchar(50) DEFAULT NULL,
  `self_card` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`act_no`),
  KEY `FKE5A6EEF9BC896CCE` (`marketing_catalog`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table marketing_act
#

LOCK TABLES `marketing_act` WRITE;
/*!40000 ALTER TABLE `marketing_act` DISABLE KEYS */;
INSERT INTO `marketing_act` VALUES (168,'1111111','测试',0,0,'0','00','',NULL,'2011-09-11',NULL,'2011-08-08 15:58:19',NULL,NULL,'11',NULL,0,NULL,NULL,'2011-08-08',1,2,15,0,0,0,0,NULL,80,1,'2','1');
INSERT INTO `marketing_act` VALUES (169,'111111','测试2',0,100,'0','00','',NULL,'2011-09-11',NULL,'2011-08-08 15:59:22',NULL,NULL,'22',NULL,0,NULL,NULL,'2011-08-08',1,2,15,0,0,0,0,'2',81,2,NULL,'1');
INSERT INTO `marketing_act` VALUES (171,'1111111111','111',5,0,'0','00','',NULL,'2011-09-11',NULL,'2011-08-08 19:39:23',NULL,NULL,'11111',NULL,0,NULL,NULL,'2011-08-08',1,4,15,0,0,0,0,NULL,83,1,'yuan4444','0');
INSERT INTO `marketing_act` VALUES (172,'11111','44',5,0,'0','00','',NULL,'2011-09-11',NULL,'2011-08-08 19:48:17',NULL,NULL,'111',NULL,0,NULL,NULL,'2011-08-08',111,3,15,0,0,0,0,'1',84,2,'0','0');
/*!40000 ALTER TABLE `marketing_act` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table marketing_act_terminal
#

CREATE TABLE `marketing_act_terminal` (
  `marketing_act` bigint(20) NOT NULL,
  `terminal` bigint(20) NOT NULL,
  PRIMARY KEY (`marketing_act`,`terminal`),
  KEY `FK43F7C73196520058` (`terminal`),
  KEY `FK43F7C731DDF44BC0` (`marketing_act`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table marketing_act_terminal
#

LOCK TABLES `marketing_act_terminal` WRITE;
/*!40000 ALTER TABLE `marketing_act_terminal` DISABLE KEYS */;
INSERT INTO `marketing_act_terminal` VALUES (168,85);
INSERT INTO `marketing_act_terminal` VALUES (169,85);
INSERT INTO `marketing_act_terminal` VALUES (171,85);
INSERT INTO `marketing_act_terminal` VALUES (172,85);
/*!40000 ALTER TABLE `marketing_act_terminal` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table marketing_catalog
#

CREATE TABLE `marketing_catalog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `catalog_name` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

#
# Dumping data for table marketing_catalog
#

LOCK TABLES `marketing_catalog` WRITE;
/*!40000 ALTER TABLE `marketing_catalog` DISABLE KEYS */;
INSERT INTO `marketing_catalog` VALUES (15,'测试','2011-08-08 15:57:31','2011-08-08 15:57:31',0);
/*!40000 ALTER TABLE `marketing_catalog` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table mms_template
#

CREATE TABLE `mms_template` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `title1` varchar(200) NOT NULL,
  `title2` varchar(200) NOT NULL,
  `card_before` varchar(200) NOT NULL,
  `coupon_id_before` varchar(200) NOT NULL,
  `value_before` varchar(200) NOT NULL,
  `period_before` varchar(200) NOT NULL,
  `ending` varchar(200) NOT NULL,
  `type` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table mms_template
#

LOCK TABLES `mms_template` WRITE;
/*!40000 ALTER TABLE `mms_template` DISABLE KEYS */;
INSERT INTO `mms_template` VALUES (80,'0','0','0','111','111','111','1111','1');
INSERT INTO `mms_template` VALUES (81,'22','22','22','222','222','222','2222','2');
INSERT INTO `mms_template` VALUES (83,'0','0','0','111','11','111','1111','2');
INSERT INTO `mms_template` VALUES (84,'0','0','0','111','111','111','1111','2');
/*!40000 ALTER TABLE `mms_template` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table oper
#

CREATE TABLE `oper` (
  `oper_no` bigint(20) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `gen_time` datetime DEFAULT NULL,
  `oper_name` varchar(24) NOT NULL,
  `oper_pwd` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`oper_no`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table oper
#

LOCK TABLES `oper` WRITE;
/*!40000 ALTER TABLE `oper` DISABLE KEYS */;
INSERT INTO `oper` VALUES (1,b'1',NULL,'biz','123',5);
INSERT INTO `oper` VALUES (2,b'1',NULL,'admin','123',2);
INSERT INTO `oper` VALUES (22,b'1','2011-05-21 17:21:03','send','123',0);
INSERT INTO `oper` VALUES (31,b'1','2011-07-10 19:51:33','check','123',0);
/*!40000 ALTER TABLE `oper` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table oper_roles
#

CREATE TABLE `oper_roles` (
  `oper` bigint(20) NOT NULL,
  `roles` bigint(20) NOT NULL,
  PRIMARY KEY (`oper`,`roles`),
  KEY `FKFCBCA58C3771F99F` (`roles`),
  KEY `FKFCBCA58CD87CD7D7` (`oper`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table oper_roles
#

LOCK TABLES `oper_roles` WRITE;
/*!40000 ALTER TABLE `oper_roles` DISABLE KEYS */;
INSERT INTO `oper_roles` VALUES (1,2);
INSERT INTO `oper_roles` VALUES (1,5);
INSERT INTO `oper_roles` VALUES (2,1);
INSERT INTO `oper_roles` VALUES (16,1);
INSERT INTO `oper_roles` VALUES (17,2);
INSERT INTO `oper_roles` VALUES (17,3);
INSERT INTO `oper_roles` VALUES (18,2);
INSERT INTO `oper_roles` VALUES (18,3);
INSERT INTO `oper_roles` VALUES (19,1);
INSERT INTO `oper_roles` VALUES (22,5);
INSERT INTO `oper_roles` VALUES (31,3);
/*!40000 ALTER TABLE `oper_roles` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table partner
#

CREATE TABLE `partner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `partner_name` varchar(32) NOT NULL,
  `partner_no` varchar(32) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `partner_catalog` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD0BCDCC846B5434E` (`partner_catalog`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

#
# Dumping data for table partner
#

LOCK TABLES `partner` WRITE;
/*!40000 ALTER TABLE `partner` DISABLE KEYS */;
INSERT INTO `partner` VALUES (26,'测试','1234',0,NULL);
/*!40000 ALTER TABLE `partner` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table partner_catalog
#

CREATE TABLE `partner_catalog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `catalog_name` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `catalog_no` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

#
# Dumping data for table partner_catalog
#

LOCK TABLES `partner_catalog` WRITE;
/*!40000 ALTER TABLE `partner_catalog` DISABLE KEYS */;
/*!40000 ALTER TABLE `partner_catalog` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table resp_status
#

CREATE TABLE `resp_status` (
  `id` bigint(16) NOT NULL,
  `coupon_id` bigint(20) NOT NULL DEFAULT '0',
  `resp_status` varchar(2) DEFAULT '0',
  `resp_desc` varchar(500) DEFAULT NULL,
  `mms_send_list_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table resp_status
#

LOCK TABLES `resp_status` WRITE;
/*!40000 ALTER TABLE `resp_status` DISABLE KEYS */;
INSERT INTO `resp_status` VALUES (11806,657281025578,'2','connect error',62);
INSERT INTO `resp_status` VALUES (11807,663248090773,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11808,663224035071,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11809,663226056978,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11810,663262030014,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11811,663269066095,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11812,663287028557,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11813,663312019715,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11814,663314018616,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11815,663318061626,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11816,663221097832,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11817,663222052437,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11818,663223084995,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11819,663225067704,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11820,663227080126,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11821,663228081279,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11822,663229081443,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11823,663230053059,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11824,663232017258,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11825,663231082226,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11826,663234039322,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11827,663233062054,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11828,663235066008,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11829,663236079060,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11830,663238012229,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11831,663237018951,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11832,663239055060,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11833,663241045793,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11834,663240048505,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11835,663243037799,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11836,663242010621,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11837,663244077513,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11838,663245018778,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11839,663246046941,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11840,663247094985,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11841,663250092166,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11842,663249040163,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11843,663252009449,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11844,663251008809,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11845,663253063711,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11846,663254080485,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11847,663255060813,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11848,663256095383,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11849,663257046262,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11850,663258039260,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11851,663260062618,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11852,663259028705,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11853,663261092776,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11854,663263060608,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11855,663264041038,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11856,663265000977,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11857,663268005547,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11858,663266006977,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11859,663267039350,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11860,663270061144,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11861,663271049120,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11862,663272003439,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11863,663273076258,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11864,663275022136,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11865,663274019865,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11866,663276022495,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11867,663277014692,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11868,663279073939,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11869,663278014648,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11870,663280042787,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11871,663282013158,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11872,663281086192,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11873,663284006346,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11874,663283032503,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11875,663285002194,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11876,663286035333,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11877,663288020520,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11878,663289091317,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11879,663290038079,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11880,663291042350,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11881,663293050376,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11882,663292083272,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11883,663295095550,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11884,663294058173,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11885,663296081611,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11886,663298048826,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11887,663299036039,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11888,663297014640,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11889,663300067992,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11890,663302031773,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11891,663301005513,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11892,663303088085,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11893,663305008809,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11894,663304063744,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11895,663306013790,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11896,663307030701,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11897,663308073454,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11898,663309047484,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11899,663310084254,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11900,663311022947,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11901,663313038891,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11902,663315016537,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11903,663316023779,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11904,663317068589,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11905,663319083294,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11906,663321070207,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11907,663320064366,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11908,663322053661,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11909,663323037835,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11910,663324044273,'2','connect error',64);
INSERT INTO `resp_status` VALUES (11911,663226056978,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11912,663248090773,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11913,663224035071,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11914,663262030014,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11915,663269066095,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11916,663287028557,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11917,663312019715,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11918,663314018616,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11919,663318061626,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11920,663223084995,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11921,663221097832,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11922,663222052437,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11923,663227080126,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11924,663225067704,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11925,663228081279,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11926,663230053059,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11927,663229081443,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11928,663231082226,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11929,663233062054,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11930,663234039322,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11931,663232017258,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11932,663236079060,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11933,663237018951,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11934,663235066008,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11935,663238012229,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11936,663240048505,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11937,663241045793,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11938,663239055060,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11939,663243037799,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11940,663242010621,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11941,663244077513,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11942,663245018778,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11943,663247094985,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11944,663246046941,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11945,663249040163,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11946,663250092166,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11947,663251008809,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11948,663253063711,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11949,663254080485,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11950,663252009449,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11951,663255060813,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11952,663257046262,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11953,663256095383,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11954,663258039260,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11955,663260062618,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11956,663259028705,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11957,663263060608,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11958,663264041038,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11959,663261092776,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11960,663265000977,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11961,663266006977,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11962,663268005547,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11963,663267039350,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11964,663270061144,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11965,663271049120,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11966,663272003439,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11967,663273076258,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11968,663275022136,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11969,663274019865,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11970,663276022495,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11971,663277014692,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11972,663278014648,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11973,663280042787,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11974,663279073939,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11975,663281086192,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11976,663282013158,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11977,663284006346,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11978,663285002194,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11979,663283032503,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11980,663286035333,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11981,663288020520,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11982,663289091317,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11983,663290038079,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11984,663291042350,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11985,663292083272,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11986,663293050376,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11987,663294058173,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11988,663295095550,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11989,663296081611,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11990,663298048826,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11991,663297014640,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11992,663299036039,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11993,663301005513,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11994,663300067992,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11995,663302031773,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11996,663303088085,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11997,663305008809,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11998,663304063744,'2','connect error',67);
INSERT INTO `resp_status` VALUES (11999,663306013790,'2','connect error',67);
INSERT INTO `resp_status` VALUES (12000,663307030701,'2','connect error',67);
INSERT INTO `resp_status` VALUES (12001,663309047484,'2','connect error',67);
INSERT INTO `resp_status` VALUES (12002,663308073454,'2','connect error',67);
INSERT INTO `resp_status` VALUES (12003,663310084254,'2','connect error',67);
INSERT INTO `resp_status` VALUES (12004,663311022947,'2','connect error',67);
INSERT INTO `resp_status` VALUES (12005,663313038891,'2','connect error',67);
INSERT INTO `resp_status` VALUES (12006,663315016537,'2','connect error',67);
INSERT INTO `resp_status` VALUES (12007,663317068589,'2','connect error',67);
INSERT INTO `resp_status` VALUES (12008,663316023779,'2','connect error',67);
INSERT INTO `resp_status` VALUES (12009,663319083294,'2','connect error',67);
INSERT INTO `resp_status` VALUES (12010,663320064366,'2','connect error',67);
INSERT INTO `resp_status` VALUES (12011,663322053661,'2','connect error',67);
INSERT INTO `resp_status` VALUES (12012,663321070207,'2','connect error',67);
INSERT INTO `resp_status` VALUES (12013,663323037835,'2','connect error',67);
INSERT INTO `resp_status` VALUES (12014,663324044273,'2','connect error',67);
/*!40000 ALTER TABLE `resp_status` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table roles
#

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(24) NOT NULL,
  `role_no` varchar(16) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

#
# Dumping data for table roles
#

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'系统管理员','ROLE_ADMIN',30);
INSERT INTO `roles` VALUES (2,'业务员','ROLE_BUSINESS',14);
INSERT INTO `roles` VALUES (3,'审核员','ROLE_CHECK',14);
INSERT INTO `roles` VALUES (4,'系统查询','ROLE_QUERY',7);
INSERT INTO `roles` VALUES (5,'发送员','ROLE_SEND',10);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table send_list
#

CREATE TABLE `send_list` (
  `id` bigint(16) NOT NULL,
  `total_count` bigint(16) DEFAULT '0',
  `success_count` bigint(16) DEFAULT '0',
  `send_status` int(11) DEFAULT '0',
  `submit_time` datetime DEFAULT NULL,
  `act_name` varchar(100) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `ms_type` int(11) DEFAULT NULL,
  `act_no` bigint(20) DEFAULT NULL,
  `message` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table send_list
#

LOCK TABLES `send_list` WRITE;
/*!40000 ALTER TABLE `send_list` DISABLE KEYS */;
INSERT INTO `send_list` VALUES (61,0,0,1,'2011-08-11 10:23:26','...',NULL,1,NULL,'彩信发送时出错,文件格式有误');
INSERT INTO `send_list` VALUES (62,1,0,1,'2011-08-11 10:24:59','刷卡送手撕面包啦','2011-08-11 10:25:18',1,174,'...');
INSERT INTO `send_list` VALUES (63,0,0,1,'2011-08-11 10:27:42','...',NULL,1,NULL,'彩信发送时出错,文件格式有误');
INSERT INTO `send_list` VALUES (64,104,0,1,'2011-08-11 10:55:01','加油就刷中行信用卡','2011-08-11 10:55:40',1,176,'...');
INSERT INTO `send_list` VALUES (65,0,0,1,'2011-08-13 18:37:49','...',NULL,1,NULL,'彩信发送时出错,文件格式有误');
INSERT INTO `send_list` VALUES (66,0,0,1,'2011-08-13 18:44:45','...',NULL,1,NULL,'彩信发送时出错,文件格式有误');
INSERT INTO `send_list` VALUES (67,104,0,1,'2011-08-13 18:48:01','加油就刷中行信用卡','2011-08-13 18:48:40',1,176,'...');
INSERT INTO `send_list` VALUES (68,104,0,1,'2011-08-13 18:50:37','加油就刷中行信用卡','2011-08-13 18:50:55',1,176,'彩信网络连接异常,请检查网络连接');
/*!40000 ALTER TABLE `send_list` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table store
#

CREATE TABLE `store` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table store
#

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table sys_param
#

CREATE TABLE `sys_param` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_name` varchar(50) DEFAULT NULL,
  `param_value` varchar(10) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `param_key` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Dumping data for table sys_param
#

LOCK TABLES `sys_param` WRITE;
/*!40000 ALTER TABLE `sys_param` DISABLE KEYS */;
INSERT INTO `sys_param` VALUES (1,'发送配置','1',8,'SEND_MGR');
/*!40000 ALTER TABLE `sys_param` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table t_dictionary
#

CREATE TABLE `t_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyname` varchar(80) DEFAULT NULL,
  `keyvalue` varchar(80) DEFAULT NULL,
  `CLASS_NAME` varchar(80) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

#
# Dumping data for table t_dictionary
#

LOCK TABLES `t_dictionary` WRITE;
/*!40000 ALTER TABLE `t_dictionary` DISABLE KEYS */;
INSERT INTO `t_dictionary` VALUES (1,'3','审核失败','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (2,'4','已作废','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (3,'1','待审核','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (4,'5','待发放','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (5,'7','已开放','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (6,'8','已过期','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (7,'9','已关闭','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (8,'0','无效','COUPON_STATUS',0);
INSERT INTO `t_dictionary` VALUES (9,'1','有效','COUPON_STATUS',0);
INSERT INTO `t_dictionary` VALUES (10,'2','过期','COUPON_STATUS',0);
INSERT INTO `t_dictionary` VALUES (11,'3','挂失','COUPON_STATUS',0);
INSERT INTO `t_dictionary` VALUES (12,'4','已用完','COUPON_STATUS',0);
INSERT INTO `t_dictionary` VALUES (13,'1','已经发送','COUPON_MMS_STATUS',0);
INSERT INTO `t_dictionary` VALUES (16,'1','限定卡BIN','BIND_CARD',1);
INSERT INTO `t_dictionary` VALUES (17,'0','不限定卡BIN','BIND_CARD',0);
INSERT INTO `t_dictionary` VALUES (18,'0','等待发送','COUPON_MMS_STATUS',0);
INSERT INTO `t_dictionary` VALUES (19,'2','发送失败','COUPON_MMS_STATUS',0);
INSERT INTO `t_dictionary` VALUES (20,'00','兑换券','BUSINESS_TYPE',0);
INSERT INTO `t_dictionary` VALUES (21,'01','折扣券','BUSINESS_TYPE',0);
INSERT INTO `t_dictionary` VALUES (24,'6','发放中','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (25,'0','待添加客户','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (26,'2','追加客户','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (27,'0','发送中','MMS_SEND_LIST_STATUS',0);
INSERT INTO `t_dictionary` VALUES (28,'1','发送完毕','MMS_SEND_LIST_STATUS',0);
INSERT INTO `t_dictionary` VALUES (29,'0','发送成功','0',0);
INSERT INTO `t_dictionary` VALUES (30,'1','发送失败','1',0);
INSERT INTO `t_dictionary` VALUES (31,'0','短信','MS_TYPE',0);
INSERT INTO `t_dictionary` VALUES (32,'1','彩信','MS_TYPE',0);
INSERT INTO `t_dictionary` VALUES (33,'02','抵用券','BUSINESS_TYPE',0);
INSERT INTO `t_dictionary` VALUES (34,'1','列表导入','IMPORT_TYPE',2);
INSERT INTO `t_dictionary` VALUES (35,'2','全局设置','IMPORT_TYPE',1);
INSERT INTO `t_dictionary` VALUES (36,'1','电信号码自动转短信','SEND_MGR',0);
INSERT INTO `t_dictionary` VALUES (37,'2','电信号码正常发彩信','SEND_MGR',1);
INSERT INTO `t_dictionary` VALUES (38,'1','发送时不检查余额','BALANCE_MGR',0);
INSERT INTO `t_dictionary` VALUES (39,'2','发送时检查余额','BALANCE_MGR',1);
INSERT INTO `t_dictionary` VALUES (40,'1','限定本人卡','SELF_CARD',1);
INSERT INTO `t_dictionary` VALUES (41,'0','不限定本人卡','SELF_CARD',0);
INSERT INTO `t_dictionary` VALUES (42,'1','模板一','TEMPLATE_TYPE',0);
INSERT INTO `t_dictionary` VALUES (43,'2','模板二','TEMPLATE_TYPE',1);
/*!40000 ALTER TABLE `t_dictionary` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table terminal
#

CREATE TABLE `terminal` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `terminal_no` varchar(30) DEFAULT NULL,
  `partner` bigint(20) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;

#
# Dumping data for table terminal
#

LOCK TABLES `terminal` WRITE;
/*!40000 ALTER TABLE `terminal` DISABLE KEYS */;
INSERT INTO `terminal` VALUES (85,'a','123456',26,NULL);
/*!40000 ALTER TABLE `terminal` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
