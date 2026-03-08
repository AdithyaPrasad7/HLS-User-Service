CREATE TABLE IF NOT EXISTS video_details (
    id BIGSERIAL PRIMARY KEY,
    path VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT REFERENCES users (id),
    meta_data JSONB NOT NULL DEFAULT '{}'::jsonb,
    is_valid BOOL,
    created_date TIMESTAMP,
    created_by TEXT,
    last_modified_date TIMESTAMP,
    last_modified_by TEXT
);