-- clients
insert into bookstore.client_detail values ('c530b46c-93b4-11ea-bb37-0242ac130002', 'client', '$2a$10$OqS9qeIPTr7PnlC5GQqsieWlX9cU5/7K3xDXVPPv.KmNwx2hBVd6u');

-- account
insert into bookstore.account_detail values ('c530b818-93b4-11ea-bb37-0242ac130002', 'admin@mail.com', '$2a$10$lREV4Jhau/QHz50RkITYS.iSZBdEA6A4hCrQaBszuF2SziZckgVVC', 'ACTIVE');
insert into bookstore.account_detail values ('133ea938-9479-11ea-bb37-0242ac130002', 'reader@mail.com', '$2a$10$lREV4Jhau/QHz50RkITYS.iSZBdEA6A4hCrQaBszuF2SziZckgVVC', 'ACTIVE');
insert into bookstore.account_detail values ('133eaa1e-9479-11ea-bb37-0242ac130002', 'writer@mail.com', '$2a$10$lREV4Jhau/QHz50RkITYS.iSZBdEA6A4hCrQaBszuF2SziZckgVVC', 'ACTIVE');

-- user_detail@
insert into bookstore.user_detail values('c530b91c-93b4-11ea-bb37-0242ac130002', 'c530b818-93b4-11ea-bb37-0242ac130002', 'John', 'Doe', '+35679797979');
insert into bookstore.user_detail values('a8a716a8-9538-11ea-bb37-0242ac130002', '133ea938-9479-11ea-bb37-0242ac130002', 'John', 'Doe', '+35677997799');
insert into bookstore.user_detail values('a8a718b0-9538-11ea-bb37-0242ac130002', '133eaa1e-9479-11ea-bb37-0242ac130002', 'John', 'Doe', '+35699779977');

-- address
insert into bookstore.address values('c530b9f8-93b4-11ea-bb37-0242ac130002', 'c530b91c-93b4-11ea-bb37-0242ac130002', 'BILLING', 'Line 1', 'Address Line 2', 'Qormi', 'Malta', 'ABC1234');
insert into bookstore.address values('c530bac0-93b4-11ea-bb37-0242ac130002', 'c530b91c-93b4-11ea-bb37-0242ac130002', 'SHIPPING', 'Line 1', 'Address Line 2', 'Qormi', 'Malta', 'ABC1234');

-- permission_group
insert into bookstore.permission_group values ('c530bc46-93b4-11ea-bb37-0242ac130002', 'Individual Read Group', 'Individual Read Group');
insert into bookstore.permission_group values ('d430c7e6-9476-11ea-bb37-0242ac130002', 'Individual Write Group', 'Individual Write Group');

-- permission
insert into bookstore.permission values ('d430ca48-9476-11ea-bb37-0242ac130002', 'ADMIN', 'Administrator permission.');

insert into bookstore.permission values ('c530be4e-93b4-11ea-bb37-0242ac130002', 'GET_AUTHOR', 'Get Authors permission.');
insert into bookstore.permission values ('c530bf16-93b4-11ea-bb37-0242ac130002', 'CREATE_AUTHOR', 'Create Author permission.');
insert into bookstore.permission values ('c530bfd4-93b4-11ea-bb37-0242ac130002', 'UPDATE_AUTHOR', 'Update Author permission.');
insert into bookstore.permission values ('d430c5d4-9476-11ea-bb37-0242ac130002', 'DELETE_AUTHOR', 'Delete Author permission.');

insert into bookstore.permission values ('133e9204-9479-11ea-bb37-0242ac130002', 'GET_BOOK', 'Get Books permission.');
insert into bookstore.permission values ('133e9420-9479-11ea-bb37-0242ac130002', 'CREATE_BOOK', 'Create Book permission.');
insert into bookstore.permission values ('133e9524-9479-11ea-bb37-0242ac130002', 'UPDATE_BOOK', 'Update Book permission.');
insert into bookstore.permission values ('133e9696-9479-11ea-bb37-0242ac130002', 'DELETE_BOOK', 'Delete Book permission.');

insert into bookstore.permission values ('133e995c-9479-11ea-bb37-0242ac130002', 'GET_ORDER', 'Get Order permission.');
insert into bookstore.permission values ('133e9a42-9479-11ea-bb37-0242ac130002', 'CREATE_ORDER', 'Create Order permission.');
insert into bookstore.permission values ('133ea78a-9479-11ea-bb37-0242ac130002', 'UPDATE_ORDER', 'Update Order permission.');

-- permission_group_permissions
insert into bookstore.permission_group_permissions values ((select id from bookstore.permission_group where name='Individual Read Group'), (select id from bookstore.permission where name='GET_AUTHOR'));
insert into bookstore.permission_group_permissions values ((select id from bookstore.permission_group where name='Individual Read Group'), (select id from bookstore.permission where name='GET_BOOK'));
insert into bookstore.permission_group_permissions values ((select id from bookstore.permission_group where name='Individual Read Group'), (select id from bookstore.permission where name='GET_ORDER'));

insert into bookstore.permission_group_permissions values ((select id from bookstore.permission_group where name='Individual Write Group'), (select id from bookstore.permission where name='CREATE_AUTHOR'));
insert into bookstore.permission_group_permissions values ((select id from bookstore.permission_group where name='Individual Write Group'), (select id from bookstore.permission where name='UPDATE_AUTHOR'));
insert into bookstore.permission_group_permissions values ((select id from bookstore.permission_group where name='Individual Write Group'), (select id from bookstore.permission where name='DELETE_AUTHOR'));

insert into bookstore.permission_group_permissions values ((select id from bookstore.permission_group where name='Individual Write Group'), (select id from bookstore.permission where name='CREATE_BOOK'));
insert into bookstore.permission_group_permissions values ((select id from bookstore.permission_group where name='Individual Write Group'), (select id from bookstore.permission where name='UPDATE_BOOK'));
insert into bookstore.permission_group_permissions values ((select id from bookstore.permission_group where name='Individual Write Group'), (select id from bookstore.permission where name='DELETE_BOOK'));

insert into bookstore.permission_group_permissions values ((select id from bookstore.permission_group where name='Individual Write Group'), (select id from bookstore.permission where name='CREATE_ORDER'));
insert into bookstore.permission_group_permissions values ((select id from bookstore.permission_group where name='Individual Write Group'), (select id from bookstore.permission where name='UPDATE_ORDER'));

-- account_permissions
insert into bookstore.account_permissions values ('c530b818-93b4-11ea-bb37-0242ac130002', (select id from bookstore.permission where name='ADMIN'));

-- account_permission_groups
insert into bookstore.account_permission_groups values ('c530b818-93b4-11ea-bb37-0242ac130002', (select id from bookstore.permission_group where name='Individual Read Group'));
insert into bookstore.account_permission_groups values ('c530b818-93b4-11ea-bb37-0242ac130002', (select id from bookstore.permission_group where name='Individual Write Group'));

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