CREATE TABLE IF NOT EXISTS `dingul_camping`.`booking` (
  `id` BIGINT AUTO_INCREMENT,
  `price` INT NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `people_number` INT NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  `is_reviewed` TINYINT NOT NULL,
  `room_id` BIGINT NOT NULL,
  `member_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `booking_id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_booking_room1_idx` (`room_id` ASC) VISIBLE,
  INDEX `fk_booking_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `room_id`
    FOREIGN KEY (`room_id`)
    REFERENCES `dingul_camping`.`room` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_booking_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `dingul_camping`.`member` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB