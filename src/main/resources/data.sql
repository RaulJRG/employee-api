INSERT INTO employees (
    first_name,
    second_name,
    last_name,
    second_last_name,
    age,
    gender,
    birth_date,
    position,
    create_date,
    is_active
)
VALUES (
    'Raul',
    NULL,
    'Robles',
    NULL,
    26,
    'MALE',
    DATE '1999-11-12',
    'Java Developer',
    CURRENT_TIMESTAMP,
    TRUE
);