
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `started_test` tinyint NOT NULL DEFAULT '0',
  `firebase_id` varchar(29) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `firebaseID_UNIQUE` (`firebase_id`)
);

INSERT INTO `user` VALUES (1,1,'yoRz4xPWcedcXeLa01FmEQ8umT63');


CREATE TABLE `topicsections` (
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`name`)
);

INSERT INTO `topicsections` VALUES ('programming'),('science');


CREATE TABLE `topic` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `section` varchar(45) DEFAULT NULL,
  `active_currently` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `section_idx` (`section`) /*!80000 INVISIBLE */,
  CONSTRAINT `section` FOREIGN KEY (`section`) REFERENCES `topicsections` (`name`)
);

INSERT INTO `topic` VALUES (1,'Chemistry','science',0),(2,'Computer Science','programming',10),(3, 'Java','programming',20),(4,'Javascript' ,'programming',16),(5, 'React','programming',7),(6,'Spring','programming',0),(7,'Machine Learning','science',1);


CREATE TABLE `tag` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `topic_tags` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `topic_id` int(10) unsigned NOT NULL,
  `current_posts` int(11) DEFAULT '0',
  `tag_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `www` (`topic_id`),
  KEY `tag_id_f_idx` (`tag_id`),
  CONSTRAINT `tag_id_tt` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`),
  CONSTRAINT `topictags_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `post` (
  `id` varchar(45) NOT NULL,
  `correct_precent` double unsigned DEFAULT '0',
  `topic_id` int(10) unsigned NOT NULL,
  `real_posts_in_topics` int(11) NOT NULL,
  `create_date_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `topic_id` (`topic_id`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `post` VALUES ('-Lmp3hGu114fV0DDsuHy',94.73684210526315,4,3, '0000-00-00 00:00:00'),('-LmttijyGAsP5cy59tye',86.36363636363636,2,1,'0000-00-00 00:00:00'),('-LmuSk1ebwuvBwF_d_pg',93.24324324324324,5,1,'0000-00-00 00:00:00'),('-LmyGYchP1NjGKvH7E2k',88.05970149253731,4,11,'0000-00-00 00:00:00'),('-LnvlocNABGtWcTCeioP',90.69767441860465,4,14,'0000-00-00 00:00:00'),('-LnvRK1XbaH4zfzI08NT',73.68421052631578,4,13,'0000-00-00 00:00:00'),('-Lo-yZfAekoq23_rG2_i',96.29629629629629,5,2,'0000-00-00 00:00:00'),('-LqwjqVrVd3q5-aZDmF2',97.14285714285714,4,9,'0000-00-00 00:00:00'),('-LqzuVsxP0PKETfl2n3g',76.62337662337663,4,1,'0000-00-00 00:00:00'),('-LqzxgXy9SwN18CL2PvU',88.23529411764706,4,2,'0000-00-00 00:00:00'),('-LrAeQgC-3rbML_arpq7',97.46835443037975,4,10,'0000-00-00 00:00:00'),('-LrB0ZHXXAyQLTzw5d3y',93.54838709677419,4,17,'0000-00-00 00:00:00'),('-LrjmqOx_Tqb7GbvaC9J',90.32258064516128,4,22,'0000-00-00 00:00:00'),('-LrL8LVZQcp4wDqMQwei',93.05555555555556,4,18,'0000-00-00 00:00:00'),('-LrLbb0H48W58xzeCu5g',46.07843137254902,4,19,'0000-00-00 00:00:00'),('-LrLeVxjVTOKgEapnthI',85.18518518518519,4,20,'0000-00-00 00:00:00'),('-LrQ83cPb5PIbzoEUEoc',92.85714285714286,4,21,'0000-00-00 00:00:00'),('-LsxUcCceQ-0tInflGs5',94.5945945945946,4,24,'0000-00-00 00:00:00'),('-LtatysrtmTgpU8WXg1c',84.70588235294117,3,4,'0000-00-00 00:00:00'),('-Ltp60pZWpQU-NG01pkV',86.25954198473282,3,5,'0000-00-00 00:00:00'),('-LtWmpWI-4VDSH2xKQ08',96.8944099378882,3,3,'0000-00-00 00:00:00'),('-Lu-fFdMT5BGMqykk8ph',94.03973509933775,3,8,'0000-00-00 00:00:00'),('-Lu-j_qqmGWbCgMQrEjb',82.20858895705521,3,9,'0000-00-00 00:00:00'),('-Lu4Rjx9ljiGt0SF8DUL',95.71428571428572,5,4,'0000-00-00 00:00:00'),('-Lu9nuoHKuxIJcukzokC',77.56410256410257,3,12,'0000-00-00 00:00:00'),('-Luo5cXHNaaG4hOWo9fj',96.31901840490798,3,15,'0000-00-00 00:00:00'),('-LuZRncovxuyYlmJ6i3a',85.09316770186336,3,14,'0000-00-00 00:00:00'),('-Lv252EcQ18OKLNBvFPw',81.48148148148148,5,7,'0000-00-00 00:00:00'),('-Lvb7EzcRvz1HbaBk-1X',72.07792207792207,3,19,'0000-00-00 00:00:00'),('-LvbO4CQeMxKiBZlEjVX',97.26027397260275,5,9,'0000-00-00 00:00:00'),('-LvHpD_VEa0KKGyH95uo',98.01324503311258,3,18,'0000-00-00 00:00:00'),('-LzgwOvTz1THgqM4PBqg',92.3076923076923,4,29,'0000-00-00 00:00:00'),('-LzJOdOE4JBUBE0EDfHx',49.074074074074076,3,20,'0000-00-00 00:00:00'),('-M1vpQrXUidT1Iu6ZkhC',0,7,6,'0000-00-00 00:00:00'),('-M1vpTxDZtSZL04LfiYo',0,7,7,'0000-00-00 00:00:00'),('-M1vWOd2m40KuBBdbqjj',0,7,5,'0000-00-00 00:00:00'),('-M1wV_YNas-BT5PjAcMS',0,7,9,'0000-00-00 00:00:00'),('-M1Xgjvkq_BeoQxIALvX',82.6086956521739,3,21,'0000-00-00 00:00:00'),('-M1XyNwNIKAJNudOGq3o',62.5,4,22,'0000-00-00 00:00:00'),('-M25gXItam0juMhMSMM5',0,7,10,'0000-00-00 00:00:00');
