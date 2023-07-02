CREATE TABLE `tables` (
  `id_tables` INT NOT NULL auto_increment,
  capacity INT NOT NULL,
  occupied INT DEFAULT 0,
  PRIMARY KEY (`id_tables`)
);

CREATE TABLE `guest_list` (
  `id_guest_list` INT NOT NULL auto_increment,
  `name` VARCHAR(50) NOT NULL,
  `table_id` INT NOT NULL,
  `acc_guests` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_guest_list`)
);

CREATE TABLE `party_guest` (
  `id_party_guest` INT NOT NULL auto_increment,
  `name` VARCHAR(50) NOT NULL,
  `in_party` TINYINT(1),
  `acc_guests_actual` INT NOT NULL DEFAULT 0,
  `entry_time` DATETIME DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY(`id_party_guest`)
);
