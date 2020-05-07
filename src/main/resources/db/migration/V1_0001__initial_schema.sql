CREATE SCHEMA IF NOT EXISTS BOOKSTORE;

CREATE TABLE IF NOT EXISTS BOOKSTORE.AUTHOR (
    id binary(16) PRIMARY KEY,
    deleted BOOLEAN NOT NULL,
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS BOOKSTORE.BOOK (
    id binary(16) PRIMARY KEY,
    deleted BOOLEAN NOT NULL,
    author_id binary(16) NOT NULL REFERENCES BOOKSTORE.AUTHOR(id),
    name VARCHAR(250) NOT NULL,
    stock NUMERIC(4, 0) NOT NULL,
    price NUMERIC(6,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS BOOKSTORE.PURCHASE_ORDER (
    id binary(16) PRIMARY KEY,
    date_time TIMESTAMP NOT NULL,
    order_status VARCHAR(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS BOOKSTORE.ORDER_ENTRY (
    id binary(16) PRIMARY KEY,
    purchase_order_id binary(16) NOT NULL REFERENCES BOOKSTORE.PURCHASE_ORDER(id),
    book_id binary(16) NOT NULL REFERENCES BOOKSTORE.BOOK(ID),
    quantity INTEGER NOT NULL,
    final_unit_price NUMERIC(6,2),
    CONSTRAINT ORDER_ID_BOOK_ID_UNIQUE UNIQUE (purchase_order_id, book_id)
);