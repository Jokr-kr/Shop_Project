
CREATE TABLE items (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       type VARCHAR(50) NOT NULL,
                       price DECIMAL(10, 2) NOT NULL,
                       quantity INT NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);