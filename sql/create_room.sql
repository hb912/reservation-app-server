CREATE TABLE IF NOT EXISTS `dingul_camping`.`room` (
  `id` BIGINT AUTO_INCREMENT,
  `name` VARCHAR(15) NOT NULL,
  `price` INT NOT NULL,
  `content` VARCHAR(10000) NULL,
  `img_src` VARCHAR(3000) NULL,
  `room_type` VARCHAR(10) NULL,
  `icon` VARCHAR(256) NULL,
  `max_people` INT NULL,
  `min_people` INT NULL,
  `top` DOUBLE NULL,
  `right` DOUBLE NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `room_id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB