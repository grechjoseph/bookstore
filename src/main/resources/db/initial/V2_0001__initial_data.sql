-- permission_group
insert into bookstore.permission_group values ('c530bc46-93b4-11ea-bb37-0242ac130002', 'Individual Read Group', 'Individual Read Group');
insert into bookstore.permission_group values ('d430c7e6-9476-11ea-bb37-0242ac130002', 'Individual Write Group', 'Individual Write Group');
insert into bookstore.permission_group values ('cfd4f5fe-9922-11ea-bb37-0242ac130002', 'Front-End Group', 'Front-End Group');

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

insert into bookstore.permission values ('cfd4f496-9922-11ea-bb37-0242ac130002', 'REGISTER_ACCOUNT', 'Register an Account permission.');
insert into bookstore.permission values ('cfd4f54a-9922-11ea-bb37-0242ac130002', 'VERIFY_ACCOUNT', 'Verify an Account permission.');

-- clients
insert into bookstore.client_detail values ('c530b46c-93b4-11ea-bb37-0242ac130002', 'client', '$2a$10$OqS9qeIPTr7PnlC5GQqsieWlX9cU5/7K3xDXVPPv.KmNwx2hBVd6u');

-- account
insert into bookstore.account_detail values ('c530b818-93b4-11ea-bb37-0242ac130002', 'admin@mail.com', '$2a$10$lREV4Jhau/QHz50RkITYS.iSZBdEA6A4hCrQaBszuF2SziZckgVVC', 'ACTIVE');
insert into bookstore.account_detail values ('cfd4f3d8-9922-11ea-bb37-0242ac130002', 'frontend@mail.com', '$2a$10$lREV4Jhau/QHz50RkITYS.iSZBdEA6A4hCrQaBszuF2SziZckgVVC', 'ACTIVE');

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

insert into bookstore.permission_group_permissions values ((select id from bookstore.permission_group where name='Front-End Group'), (select id from bookstore.permission where name='REGISTER_ACCOUNT'));
insert into bookstore.permission_group_permissions values ((select id from bookstore.permission_group where name='Front-End Group'), (select id from bookstore.permission where name='VERIFY_ACCOUNT'));

-- account_permissions
insert into bookstore.account_permissions values ('c530b818-93b4-11ea-bb37-0242ac130002', (select id from bookstore.permission where name='ADMIN'));

-- account_permission_groups
insert into bookstore.account_permission_groups values ('c530b818-93b4-11ea-bb37-0242ac130002', (select id from bookstore.permission_group where name='Individual Read Group'));
insert into bookstore.account_permission_groups values ('c530b818-93b4-11ea-bb37-0242ac130002', (select id from bookstore.permission_group where name='Individual Write Group'));
insert into bookstore.account_permission_groups values ('cfd4f3d8-9922-11ea-bb37-0242ac130002', (select id from bookstore.permission_group where name='Front-End Group'));