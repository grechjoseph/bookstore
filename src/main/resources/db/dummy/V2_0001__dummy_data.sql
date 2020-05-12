-- clients
insert into bookstore.client values ('c530b46c-93b4-11ea-bb37-0242ac130002', 'client', 'secret');

-- account
insert into bookstore.account values ('c530b818-93b4-11ea-bb37-0242ac130002', 'admin@mail.com', '123456', 'ACTIVE');

-- user_detail
insert into bookstore.user_detail values('c530b91c-93b4-11ea-bb37-0242ac130002', 'c530b818-93b4-11ea-bb37-0242ac130002', 'John', 'Doe', '+35679797979');

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

-- permission_group_permissions
insert into bookstore.permission_group_permissions values ('c530bc46-93b4-11ea-bb37-0242ac130002', 'c530be4e-93b4-11ea-bb37-0242ac130002');
insert into bookstore.permission_group_permissions values ('d430c7e6-9476-11ea-bb37-0242ac130002', 'c530bf16-93b4-11ea-bb37-0242ac130002');
insert into bookstore.permission_group_permissions values ('d430c7e6-9476-11ea-bb37-0242ac130002', 'c530bfd4-93b4-11ea-bb37-0242ac130002');
insert into bookstore.permission_group_permissions values ('d430c7e6-9476-11ea-bb37-0242ac130002', 'd430c5d4-9476-11ea-bb37-0242ac130002');

-- account_permissions
insert into bookstore.account_permissions values ('c530b818-93b4-11ea-bb37-0242ac130002', 'd430ca48-9476-11ea-bb37-0242ac130002');

-- account_permission_groups
insert into bookstore.account_permission_groups values ('c530b818-93b4-11ea-bb37-0242ac130002', 'c530bc46-93b4-11ea-bb37-0242ac130002');
insert into bookstore.account_permission_groups values ('c530b818-93b4-11ea-bb37-0242ac130002', 'd430c7e6-9476-11ea-bb37-0242ac130002');

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