insert into bookstore.author values ('682abafb-ca77-4509-a2f0-157ff22bae70', false, 'Joseph', 'Grech');

insert into bookstore.book values ('03fdfcc2-dea7-427f-af74-5fdaf8846f6b', false, (select id from bookstore.author limit 1), 'Book One', '100', '10.95');
insert into bookstore.book values ('47203328-a723-4c9e-9fc9-9064a93fc2be', false, (select id from bookstore.author limit 1), 'Book Two', '100', '12.50');
insert into bookstore.book values ('d17d3f1f-8813-4767-9132-266ba3c6d7ad', false, (select id from bookstore.author limit 1), 'Book Three', '100', '11.50');

insert into bookstore.purchase_order values ('fce5625d-403c-4483-b8d8-67d8c1b49351', now(), 'CREATED');

insert into bookstore.order_entry values ('acdf4b4e-b4a7-4d10-8791-8b3e600ab34a', 'fce5625d-403c-4483-b8d8-67d8c1b49351', '03fdfcc2-dea7-427f-af74-5fdaf8846f6b', '2', null);
insert into bookstore.order_entry values ('bd87334a-55d6-4fc9-8dcc-2c3b8f3a1c4b', 'fce5625d-403c-4483-b8d8-67d8c1b49351', '47203328-a723-4c9e-9fc9-9064a93fc2be', '3', null);