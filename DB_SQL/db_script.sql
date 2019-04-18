CREATE DATABASE IF NOT EXISTS amico;
use amico;
GRANT ALL PRIVILEGES ON *.* TO 'amico_db_user'@'localhost' IDENTIFIED BY 'aMiCo@123$$' with GRANT option;
GRANT SELECT  ON `amico`.* TO 'amico_user'@'localhost' IDENTIFIED BY 'myteam';
GRANT SELECT,UPDATE,INSERT,DELETE  ON `amico`.* TO 'amico_app_user'@'localhost' IDENTIFIED BY 'aMiCo@123$$';