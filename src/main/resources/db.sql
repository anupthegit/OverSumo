CREATE DATABASE  IF NOT EXISTS `overwatch`;
USE `overwatch`;

CREATE TABLE IF NOT EXISTS `abilities` (
  `id` int(11) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(10000) NOT NULL,
  `is_ultimate` boolean NOT NULL,
  `hero_id` int(11) not NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_hero_ability_id` FOREIGN KEY (`hero_id`) REFERENCES `heroes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `heroes` (
  `id` int(11) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `real_name` varchar(255) NOT NULL,
  `health` int NOT NULL,
  `armour` int NOT NULL,
  `shield` int NOT NULL
) DEFAULT CHARSET=utf8;