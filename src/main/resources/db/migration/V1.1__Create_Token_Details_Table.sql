CREATE TABLE IF NOT EXISTS token_details (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT REFERENCES users (id),
    is_valid BOOL,
    expiry TIMESTAMP,
    created_date TIMESTAMP,
    created_by TEXT,
    last_modified_date TIMESTAMP,
    last_modified_by TEXT
);