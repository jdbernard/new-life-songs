INSERT INTO SERVICES (date, service_type) values
('2015-02-01', 'SUN_AM'),
('2015-02-01', 'SUN_PM'),
('2015-02-04', 'WED'),
('2015-02-08', 'SUN_AM'),
('2015-02-08', 'SUN_PM'),
('2015-02-11', 'WED'),
('2015-02-15', 'SUN_AM'),
('2015-02-15', 'SUN_PM');

INSERT INTO songs (name, artists) VALUES
('Breathe On Us', 'Kari Jobe'),
('How Great Is Our God', 'Chris Tomlin'),
('Glorious', 'Martha Munizzi'),
('Rez Power', 'Israel Houghton');

INSERT INTO performances (service_id, song_id, pianist, organist, bassist, drummer, guitarist, leader) VALUES
(1, 1, 'Jared Wood', null, 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser; Tony Bagliore', 'Rachel Wood'),
(1, 2, 'Jared Wood', null, 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser; Tony Bagliore', 'Rachel Wood'),
(1, 3, 'Jared Wood', null, 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser; Tony Bagliore', 'Rachel Wood'),
(2, 2, 'Trevor Delano', 'Connie Bernard', 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser', 'Rachel Wood'),
(2, 3, 'Trevor Delano', 'Connie Bernard', 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser', 'Rachel Wood'),
(2, 4, 'Trevor Delano', 'Connie Bernard', 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser', 'Rachel Wood'),
(3, 1, 'Rachel Wood', 'Krista Hatcher', 'Jonathan Bernard', 'Jared Wood', 'Tony Bagliore', 'Rachel Wood'),
(3, 2, 'Rachel Wood', 'Krista Hatcher', 'Jonathan Bernard', 'Jared Wood', 'Tony Bagliore', 'Rachel Wood'),
(4, 3, 'Trevor Delano', null, 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser', 'Rachel Wood'),
(5, 4, 'Jared Wood', null, 'Jonathan Bernard', 'Christian Thompson', 'Tony Bagliore', 'Rachel Wood'),
(6, 1, 'Jared Wood', null, 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser; Tony Bagliore', 'Rachel Wood'),
(7, 2, 'Trevor Delano', null, 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser; Tony Bagliore', 'Rachel Wood'),
(8, 3, 'Jared Wood', null, 'Jonathan Bernard', 'Christian Thompson', 'Andrew Fraiser; Tony Bagliore', 'Rachel Wood');

INSERT INTO users (username, pwd, role) VALUES
('admin', '', 'admin'),
('test', '', '');
