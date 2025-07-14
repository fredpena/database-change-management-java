DELETE
FROM tenant;


insert into tenant(tenant_id, tenant_name)
values (1, 'Tenant 1'),
       (2, 'Tenant 2'),
       (3, 'Tenant 3');

SELECT SETVAL('tenant_tenant_id_seq', (SELECT MAX(tenant_id) FROM tenant) + 1, false);

DELETE
FROM person;


insert into person(tenant_id, first_name, last_name, email, address, phone_number, birth_date)
values (1, 'jigrormo', 'Lane', 'eula.lane@jigrormo.ye', '1395 Jigror Park', '(762) 526-5961', '1955-12-07'),
       (2, 'zun', 'Rodriquez', 'barry.rodriquez@zun.mm', '216 Zunnij Grove', '(267) 955-5124', '2014-12-07'),
       (3, 'capfad', 'Selvi', 'eugenia.selvi@capfad.vn', '1016 Capfad View', '(680) 368-2192', '1974-11-22'),
       (1, 'dec', 'Miles', 'alejandro.miles@dec.bn', '214 Decde River', '(281) 301-2039', '2015-01-09'),
       (1, 'bivo', 'Tesi', 'cora.tesi@bivo.yt', '1050 Bivo Way', '(600) 616-7955', '1973-03-08'),
       (2, 'judbilo', 'Ishii', 'marguerite.ishii@judbilo.gn', '1734 Judbi Grove', '(882) 813-1374', '1938-12-04'),
       (3, 'joraf', 'Jacobs', 'mildred.jacobs@joraf.wf', '1143 Joraf Way', '(642) 665-1763', '1968-07-08'),
       (1, 'kem', 'Goodman', 'gene.goodman@kem.tl', '287 Kemdol Street', '(383) 458-2132', '2011-05-19'),
       (1, 'odeter', 'Bennett', 'lettie.bennett@odeter.bb', '1302 Odeter Circle', '(769) 335-6771', '1960-07-23'),
       (1, 'lisohuje', 'Leach', 'mabel.leach@lisohuje.vi', '1563 Lisoh Square', '(803) 586-8035', '1947-06-30'),
       (2, 'duod', 'Miccinesi', 'jordan.miccinesi@duod.gy', '842 Duod Lane', '(531) 919-2280', '1983-08-11'),
       (2, 'nowufpus', 'Parkes', 'marie.parkes@nowufpus.ph', '1624 Nowuf Plaza', '(814) 667-8937', '1944-06-11'),
       (3, 'kagu', 'Gray', 'rose.gray@kagu.hr', '1325 Kagu Loop', '(713) 311-8766', '1959-06-11'),
       (3, 'fef', 'Stokes', 'garrett.stokes@fef.bg', '310 Feffo Grove', '(381) 421-2371', '2010-03-22'),
       (1, 'derwogi', 'Matthieu', 'barbara.matthieu@derwogi.jm', '1888 Derwo Park', '(940) 463-7299', '1931-03-18'),
       (1, 'wehovuce', 'Rhodes', 'jean.rhodes@wehovuce.gu', '1500 Wehovu Boulevard', '(777) 435-9570', '1950-08-25'),
       (1, 'zamum', 'Romoli', 'jack.romoli@zamum.bw', '984 Zamum Drive', '(517) 393-9630', '1976-06-20'),
       (2, 'dunebuh', 'Holden', 'pearl.holden@dunebuh.cr', '1497 Dune Parkway', '(711) 904-3669', '1950-10-16'),
       (2, 'repiwid', 'Montero', 'belle.montero@repiwid.si', '1836 Repi Terrace', '(935) 404-4792', '1933-11-08'),
       (3, 'razuppa', 'Molina', 'olive.molina@razuppa.ga', '1805 Razup Extension', '(935) 267-8492', '1935-05-21');


