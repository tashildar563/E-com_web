CREATE TABLE item_inventory (
    id bigserial primary key,
    barcode_info_id bigserial not null,
    name VARCHAR(255) not null,
    description TEXT  null,
    category VARCHAR(255) null,
    sub_category VARCHAR(255) null,
    brand VARCHAR(255) null,
    model VARCHAR(255) null,
    color VARCHAR(255) null,
    size VARCHAR(255) null,
    price DECIMAL(10, 2) null,
    created_by VARCHAR(255) not null,
    created_on TIMESTAMP not null,
    updated_by VARCHAR(255) not null,
    updated_on TIMESTAMP not  null,
    is_deleted boolean default false,
    image TEXT null
);

CREATE TABLE barcode_info(
    id bigserial not null,
    barcode varchar(255) not null,
    created_by VARCHAR(255) not null,
    created_on TIMESTAMP not null,
    is_deleted boolean default false
);