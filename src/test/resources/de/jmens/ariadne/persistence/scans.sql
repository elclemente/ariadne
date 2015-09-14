delete from `scan`;

insert into `scan` (`scan_id`, `start`, `finish`) values
('1234567890abcdef1234567890abcdef', '2015-01-01 10:00:00', '2015-01-01 12:00:00'),
('2234567890abcdef1234567890abcdef', '2015-01-04 11:00:00', '2015-01-05 02:03:00'),
('3234567890abcdef1234567890abcdef', '2015-01-08 10:00:00', null);