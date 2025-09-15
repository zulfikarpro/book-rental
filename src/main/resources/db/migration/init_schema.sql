CREATE TABLE books (
isbn VARCHAR(20) PRIMARY KEY,
title VARCHAR(255) NOT NULL,
author VARCHAR(255) NOT NULL,
CONSTRAINT uq_isbn_title_author UNIQUE (isbn, title, author)
);


CREATE TABLE book_copies (
copy_id BIGSERIAL PRIMARY KEY,
isbn VARCHAR(20) NOT NULL REFERENCES books(isbn),
available BOOLEAN DEFAULT TRUE
);


CREATE TABLE borrowers (
user_id BIGSERIAL PRIMARY KEY,
full_name VARCHAR(255) NOT NULL,
email VARCHAR(255) UNIQUE NOT NULL
);


CREATE TABLE transactions (
transaction_id BIGSERIAL PRIMARY KEY,
user_id BIGINT NOT NULL REFERENCES borrowers(user_id),
copy_id BIGINT NOT NULL REFERENCES book_copies(copy_id),
borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
return_date TIMESTAMP NULL,
status VARCHAR(20) DEFAULT 'BORROWED'
);


-- ensure no two active borrows for same copy
CREATE UNIQUE INDEX IF NOT EXISTS uq_copy_borrowed
ON transactions(copy_id)
WHERE status = 'BORROWED' AND return_date IS NULL;