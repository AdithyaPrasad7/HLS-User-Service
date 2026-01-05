CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(50) NOT NULL UNIQUE,
    phone VARCHAR(30),
    password_hashed TEXT,
    password_salt TEXT,
    created_date TIMESTAMP,
    created_by TEXT,
    last_modified_date TIMESTAMP,
    last_modified_by TEXT
);