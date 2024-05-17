CREATE TABLE customers (
    id UUID,
    full_name VARCHAR(255),
    email TEXT NOT NULL,
    is_active BOOL DEFAULT false,
    CONSTRAINT pk_customer PRIMARY KEY(id)
);

CREATE TABLE customer_addresses (
    id UUID,
    address TEXT NOT NULL,
    customer_id UUID,
    CONSTRAINT fk_customer_address_customer
        FOREIGN KEY(customer_id)
        REFERENCES customers(id)
        ON DELETE CASCADE
);
