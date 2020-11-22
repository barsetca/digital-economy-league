DROP TABLE IF EXISTS prices;
DROP TABLE IF EXISTS products;


CREATE TABLE IF NOT EXISTS products
(
    id   bigserial PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS prices
(
    id         bigserial PRIMARY KEY,
    price      DECIMAL(10, 2) CHECK (price > 0),
    date       DATE DEFAULT now(),
    product_id BIGINT REFERENCES products (id)
);



