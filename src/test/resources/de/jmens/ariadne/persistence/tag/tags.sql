delete from `tag`;

insert into `tag` (id, file_id, scan_id, album, artist, genre, title, track, year, path) values
(1, '1d582abfa4fb41758ee328f474a6f762', null, 'Demo Album 1', 'Demo Artist 1', 'Funk', 'Demo Title 1', 'Track 1', '2005', '/tmp/foo'),
(2, '2d582abfa4fb41758ee328f474a6f762', null, 'Demo Album 2', 'Demo Artist 2', 'Gore', 'Demo Title 2', 'Track 1', '2005', '/tmp/bar'),
(3, '3d582abfa4fb41758ee328f474a6f762', null, 'Demo Album 3', 'Demo Artist 3', 'Vocal', 'Demo Title 3', 'Track 1', '2005', '/tmp/buzz'),
(4, '4d582abfa4fb41758ee328f474a6f762', null, 'Demo Album 4', 'Demo Artist 4', 'Jazz', 'Demo Title 4', 'Track 1', '2005', '/tmp/doe'),
(5, '5d582abfa4fb41758ee328f474a6f762', null, 'Demo Album 5', 'Demo Artist 5', 'Grunt', 'Demo Title 5', 'Track 1', '2005', '/tmp/fizz')
