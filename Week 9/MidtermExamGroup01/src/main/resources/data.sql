CREATE TABLE CUSTOMER (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_time TIMESTAMP NOT NULL,
    updated_time TIMESTAMP NOT NULL
);

CREATE TABLE PRODUCT (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_time TIMESTAMP NOT NULL,
    updated_time TIMESTAMP NOT NULL
);

CREATE TABLE INVOICE (
    id VARCHAR(255) PRIMARY KEY,
    customer_id VARCHAR(255) NOT NULL,
    invoice_amount DECIMAL(10, 2) NOT NULL,
    invoice_date TIMESTAMP NOT NULL,
    created_time TIMESTAMP NOT NULL,
    updated_time TIMESTAMP NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER(id)
);

CREATE TABLE INVOICE_PRODUCT (
    invoice_id VARCHAR(255) NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    created_time TIMESTAMP NOT NULL,
    updated_time TIMESTAMP NOT NULL,
    PRIMARY KEY (invoice_id, product_id),
    FOREIGN KEY (invoice_id) REFERENCES INVOICE(id),
    FOREIGN KEY (product_id) REFERENCES PRODUCT(id)
);
