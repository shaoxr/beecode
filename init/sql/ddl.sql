CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(16) NOT NULL,
  `next_val` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sequence_name`)
)