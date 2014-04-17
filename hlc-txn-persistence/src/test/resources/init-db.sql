drop database if exists hlc;
create database hlc;
use hlc;

grant all on hlc.* to 'dev'@'localhost' identified by 'dev';
grant select,update,insert,delete on hlc.* to 'dev'@'localhost' identified by 'dev';
