CREATE TABLE IF NOT EXISTS `dingul_camping`.`member` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(20) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `email` VARCHAR(50) NULL,
  `phone_number` VARCHAR(20) NULL,
  `role` VARCHAR(10) NOT NULL,
  `provider` VARCHAR(20) NULL,
  `refresh_token` VARCHAR(64) NULL,
  `created_date` DATETIME(6) NULL,
  `last_modified_date` DATETIME(6) NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB