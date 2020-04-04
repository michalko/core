
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

INSERT INTO `topic` VALUES (1,'Chemistry','science',0),(2,'Computer Science','programming',10),(3,'Java','programming',20),(4,'Javascript','programming',16),(5,'React','programming',7),(6,'Spring','programming',0),(7,'Machine Learning','science',1);


CREATE TABLE `topictags` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `topic_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `www` (`topic_id`),
  CONSTRAINT `topictags_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`)
);

INSERT INTO `topictags` VALUES (1,'annotations',3),(2,'classes',3),(3,'collections',3),(4,'database',3),(5,'executors',3),(6,'futures',3),(7,'garbage collection',3),(8,'generics',3),(9,'interfaces',3),(10,'polymorphism',3),(11,'sorting',3),(12,'threads',3),(13,'classes',4),(14,'closures',4),(15,'conversions',4),(16,'es2019',4),(17,'generators',4),(18,'hoisting',4),(19,'iterators',4),(20,'prototype',4),(21,'regex',4),(22,'Functional Components',5),(23,'HOC',5),(24,'Hooks',5),(25,'Lifecycle',5),(26,'Redux',5),(27,'memo',5),(28,'useEffect',5);


CREATE TABLE `post` (
  `id` varchar(45) NOT NULL,
  `correct_precent` double unsigned DEFAULT '0',
  `topic_id` int unsigned NOT NULL,
  `real_posts_in_topics` int NOT NULL,
  `topic_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `topic_id` (`topic_id`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`)
);

INSERT INTO `post` VALUES ('-Lmp3hGu114fV0DDsuHy',94.73684210526315,4,3,'Javascript'),('-LmttijyGAsP5cy59tye',86.36363636363636,2,1,'Computer Science'),('-LmuSk1ebwuvBwF_d_pg',93.24324324324324,5,1,'React'),('-LmyGYchP1NjGKvH7E2k',88.05970149253731,4,11,'Javascript'),('-LnvlocNABGtWcTCeioP',90.69767441860465,4,14,'Javascript'),('-LnvRK1XbaH4zfzI08NT',73.68421052631578,4,13,'Javascript'),('-Lo-yZfAekoq23_rG2_i',96.29629629629629,5,2,'React'),('-LqwjqVrVd3q5-aZDmF2',97.14285714285714,4,9,'Javascript'),('-LqzuVsxP0PKETfl2n3g',76.62337662337663,4,1,'Javascript'),('-LqzxgXy9SwN18CL2PvU',88.23529411764706,4,2,'Javascript'),('-LrAeQgC-3rbML_arpq7',97.46835443037975,4,10,'Javascript'),('-LrB0ZHXXAyQLTzw5d3y',93.54838709677419,4,17,'Javascript'),('-LrjmqOx_Tqb7GbvaC9J',90.32258064516128,4,22,'Javascript'),('-LrL8LVZQcp4wDqMQwei',93.05555555555556,4,18,'Javascript'),('-LrLbb0H48W58xzeCu5g',46.07843137254902,4,19,'Javascript'),('-LrLeVxjVTOKgEapnthI',85.18518518518519,4,20,'Javascript'),('-LrQ83cPb5PIbzoEUEoc',92.85714285714286,4,21,'Javascript'),('-LsxUcCceQ-0tInflGs5',94.5945945945946,4,24,'Javascript'),('-LtatysrtmTgpU8WXg1c',84.70588235294117,3,4,'Java'),('-Ltp60pZWpQU-NG01pkV',86.25954198473282,3,5,'Java'),('-LtWmpWI-4VDSH2xKQ08',96.8944099378882,3,3,'Java'),('-Lu-fFdMT5BGMqykk8ph',94.03973509933775,3,8,'Java'),('-Lu-j_qqmGWbCgMQrEjb',82.20858895705521,3,9,'Java'),('-Lu4Rjx9ljiGt0SF8DUL',95.71428571428572,5,4,'React'),('-Lu9nuoHKuxIJcukzokC',77.56410256410257,3,12,'Java'),('-Luo5cXHNaaG4hOWo9fj',96.31901840490798,3,15,'Java'),('-LuZRncovxuyYlmJ6i3a',85.09316770186336,3,14,'Java'),('-Lv252EcQ18OKLNBvFPw',81.48148148148148,5,7,'React'),('-Lvb7EzcRvz1HbaBk-1X',72.07792207792207,3,19,'Java'),('-LvbO4CQeMxKiBZlEjVX',97.26027397260275,5,9,'React'),('-LvHpD_VEa0KKGyH95uo',98.01324503311258,3,18,'Java'),('-LzgwOvTz1THgqM4PBqg',92.3076923076923,4,29,'Javascript'),('-LzJOdOE4JBUBE0EDfHx',49.074074074074076,3,20,'Java'),('-M1vpQrXUidT1Iu6ZkhC',0,7,6,'Machine Learning'),('-M1vpTxDZtSZL04LfiYo',0,7,7,'Machine Learning'),('-M1vWOd2m40KuBBdbqjj',0,7,5,'Machine Learning'),('-M1wV_YNas-BT5PjAcMS',0,7,9,'Machine Learning'),('-M1Xgjvkq_BeoQxIALvX',82.6086956521739,3,21,'Java'),('-M1XyNwNIKAJNudOGq3o',62.5,4,22,'Javascript'),('-M25gXItam0juMhMSMM5',0,7,10,'Machine Learning');

