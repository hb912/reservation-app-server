create table room_img_src (
	room_id bigint not null,
	img_src varchar(255),
    CONSTRAINT `fk_room_img_src_room1`
    FOREIGN KEY (`room_id`)
    REFERENCES `dingul_camping`.`room` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)

