-- account
insert into bookstore.account_detail values ('133ea938-9479-11ea-bb37-0242ac130002', 'reader@mail.com', '$2a$10$lREV4Jhau/QHz50RkITYS.iSZBdEA6A4hCrQaBszuF2SziZckgVVC', 'ACTIVE');
insert into bookstore.account_detail values ('133eaa1e-9479-11ea-bb37-0242ac130002', 'writer@mail.com', '$2a$10$lREV4Jhau/QHz50RkITYS.iSZBdEA6A4hCrQaBszuF2SziZckgVVC', 'ACTIVE');

-- user_details
insert into bookstore.user_detail values('c530b91c-93b4-11ea-bb37-0242ac130002', 'c530b818-93b4-11ea-bb37-0242ac130002', 'John', 'Doe', '+35679797979');
insert into bookstore.user_detail values('a8a716a8-9538-11ea-bb37-0242ac130002', '133ea938-9479-11ea-bb37-0242ac130002', 'John', 'Doe', '+35677997799');
insert into bookstore.user_detail values('a8a718b0-9538-11ea-bb37-0242ac130002', '133eaa1e-9479-11ea-bb37-0242ac130002', 'John', 'Doe', '+35699779977');

-- user_configuration
insert into bookstore.account_configuration values ('a22cc84e-96f6-11ea-bb37-0242ac130002', 'c530b818-93b4-11ea-bb37-0242ac130002', 'EUR');
insert into bookstore.account_configuration values ('a22cca9c-96f6-11ea-bb37-0242ac130002', '133ea938-9479-11ea-bb37-0242ac130002', 'GBP');
insert into bookstore.account_configuration values ('a22ccb96-96f6-11ea-bb37-0242ac130002', '133eaa1e-9479-11ea-bb37-0242ac130002', 'GBP');

-- address
insert into bookstore.address values('c530b9f8-93b4-11ea-bb37-0242ac130002', 'c530b91c-93b4-11ea-bb37-0242ac130002', 'BILLING', 'Line 1', 'Address Line 2', 'Qormi', 'Malta', 'ABC1234');
insert into bookstore.address values('c530bac0-93b4-11ea-bb37-0242ac130002', 'c530b91c-93b4-11ea-bb37-0242ac130002', 'SHIPPING', 'Line 1', 'Address Line 2', 'Qormi', 'Malta', 'ABC1234');

-- account_permission_groups
insert into bookstore.account_permission_groups values ('133ea938-9479-11ea-bb37-0242ac130002', (select id from bookstore.permission_group where name='Individual Read Group'));

insert into bookstore.account_permission_groups values ('133eaa1e-9479-11ea-bb37-0242ac130002', (select id from bookstore.permission_group where name='Individual Write Group'));

-- authors
insert into bookstore.author values ('682abafb-ca77-4509-a2f0-157ff22bae70', false, 'Joseph', 'Grech');

-- books
insert into bookstore.book values ('03fdfcc2-dea7-427f-af74-5fdaf8846f6b', false, (select id from bookstore.author limit 1), 'Book One', '100', '10.95');
insert into bookstore.book values ('47203328-a723-4c9e-9fc9-9064a93fc2be', false, (select id from bookstore.author limit 1), 'Book Two', '100', '12.50');
insert into bookstore.book values ('d17d3f1f-8813-4767-9132-266ba3c6d7ad', false, (select id from bookstore.author limit 1), 'Book Three', '100', '11.50');

-- purchase orders
insert into bookstore.purchase_order values ('fce5625d-403c-4483-b8d8-67d8c1b49351', now(), 'CREATED');

-- order entries
insert into bookstore.order_entry values ('acdf4b4e-b4a7-4d10-8791-8b3e600ab34a', 'fce5625d-403c-4483-b8d8-67d8c1b49351', '03fdfcc2-dea7-427f-af74-5fdaf8846f6b', '2', null);
insert into bookstore.order_entry values ('bd87334a-55d6-4fc9-8dcc-2c3b8f3a1c4b', 'fce5625d-403c-4483-b8d8-67d8c1b49351', '47203328-a723-4c9e-9fc9-9064a93fc2be', '3', null);