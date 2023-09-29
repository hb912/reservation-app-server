CREATE TABLE IF NOT EXISTS `dingul_camping`.`review` (
  `review_id` BIGINT AUTO_INCREMENT,
  `title` VARCHAR(255) NULL,
  `content` VARCHAR(10000) NULL,
  `grade` DOUBLE NULL,
  `room_id` BIGINT NOT NULL,
  `booking_id` BIGINT NOT NULL,
  `member_id` BIGINT NOT NULL,
  PRIMARY KEY (`review_id`),
  UNIQUE INDEX `review_id_UNIQUE` (`review_id` ASC) VISIBLE,
  INDEX `fk_review_room1_idx` (`room_id` ASC) VISIBLE,
  INDEX `fk_review_booking1_idx` (`booking_id` ASC) VISIBLE,
  INDEX `fk_review_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_review_room1`
    FOREIGN KEY (`room_id`)
    REFERENCES `dingul_camping`.`room` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_review_booking1`
    FOREIGN KEY (`booking_id`)
    REFERENCES `dingul_camping`.`booking` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_review_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `dingul_camping`.`member` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB