CREATE TABLE addresses (
    id UUID,
    street_number VARCHAR(255),
    street_name VARCHAR(255),
    city VARCHAR(255),
    province VARCHAR(255),
    CONSTRAINT pk_address PRIMARY KEY(id)
);

CREATE TABLE customers (
    id UUID,
    full_name VARCHAR(255),
    email TEXT NOT NULL,
    is_active BOOL DEFAULT false,
    address_id UUID,
    CONSTRAINT pk_customer PRIMARY KEY(id),
    CONSTRAINT fk_customer_address
        FOREIGN KEY(address_id)
        REFERENCES addresses(id)
        ON DELETE SET NULL
);
