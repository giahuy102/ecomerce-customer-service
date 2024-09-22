INSERT INTO addresses (id, street_number, street_name, city, province)
VALUES
    ('076dd487-e99c-4b8e-9265-55d7c05c3341', '17/2A', 'Hung Vuong', 'My Tho City', 'Tien Giang');

INSERT INTO customers (id, full_name, email, is_active, address_id)
VALUES
    ('233fb0b8-2543-475e-8605-bcff99412481', 'Test customer', 'test@gmail.com', true, '076dd487-e99c-4b8e-9265-55d7c05c3341');
