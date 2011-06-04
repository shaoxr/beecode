CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(16) NOT NULL,
  `next_val` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sequence_name`)
)
CREATE TABLE `customer` (
  `id` bigint(16) NOT NULL,
  `old_name` varchar(100) DEFAULT NULL,
  `new_name` varchar(20) DEFAULT NULL,
  `count` bigint(10) DEFAULT 0,
  `act_no` bigint(20) NOT NULL,
  `file_status` bigint(2) DEFAULT 0,
  PRIMARY KEY (`id`)
)
CREATE TABLE `mms_send_list` (
  `id` bigint(16) NOT NULL,
  `total_count` bigint(16) DEFAULT 0,
  `success_count` bigint(16) DEFAULT 0,
  `send_status` int(11) DEFAULT 0,
  `submit_time` datetime DEFAULT NULL,
  `act_name` varchar(100) DEFAULT null,
  PRIMARY KEY (`id`)
)
CREATE TABLE `resp_status` (
  `id` bigint(16) NOT NULL,
  `coupon_id` bigint(20) NOT NULL,
  `resp_status` int(11) DEFAULT 0,
  `resp_desc` varchar(500) DEFAULT null,
  PRIMARY KEY (`id`)
)
CREATE TABLE `mms_template` (
  `id` bigint(20) NOT NULL,
  `title1` varchar(200) NOT NULL,
  `title2` varchar(200) NOT NULL,
  `card_before` varchar(200) NOT NULL,
  `coupon_id_before` varchar(200) NOT NULL,
  `value_before` varchar(200) NOT NULL,
  `period_before` varchar(200) NOT NULL,
  `ending` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
)