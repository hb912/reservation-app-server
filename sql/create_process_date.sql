CREATE TABLE IF NOT EXISTS `dingul_camping`.`process_date` (
  `id` BIGINT AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `booking_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`, `booking_id`),
  UNIQUE INDEX `idprocess_date_id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_process_date_booking1_idx` (`booking_id` ASC) VISIBLE,
  CONSTRAINT `fk_process_date_booking1`
    FOREIGN KEY (`booking_id`)
    REFERENCES `dingul_camping`.`booking` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB