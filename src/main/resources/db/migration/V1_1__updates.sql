ALTER TABLE `brain1`.`topic` 
ADD COLUMN `current_posts` INT NOT NULL DEFAULT 0 AFTER `active_currently`;

CREATE TABLE `post_tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` varchar(45) NOT NULL,
  `tag_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `post_id_f_idx` (`post_id`),
  KEY `tag_id_f_idx` (`tag_id`),
  CONSTRAINT `post_id_f` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `tag_id_f` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
