CREATE TABLE product (
    id bigserial primary key,
    name VARCHAR(255) not null,
    category_id bigserial,
    created_by VARCHAR(255) not null,
    created_on TIMESTAMP not null,
    updated_by VARCHAR(255) not null,
    updated_on TIMESTAMP not  null,
    is_deleted boolean default false
);

CREATE TABLE color (
    id bigserial primary key,
    name VARCHAR(255) not null,
    is_deleted boolean default false
);

CREATE TABLE size (
    id bigserial primary key,
    name VARCHAR(255) not null,
    unit VARCHAR(255) ,
    size_type VARCHAR(255) ,
    size VARCHAR(255) not null,
    is_deleted boolean default false
);

CREATE TABLE product_color_size_mapping (
    id bigserial primary key,
    product_id bigserial not null,
    color_id bigserial ,
    size_id bigserial ,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (color_id) REFERENCES color (id),
    FOREIGN KEY (size_id) REFERENCES size (id)
);