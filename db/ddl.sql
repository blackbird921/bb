/*
product
- id
- commits
- stakes
- start_date
- end_date

location
- id
- name
- address
- city
- gps_info
- phone
- contact_name
- status (NEW, APPROVED, REJECTED)
- rejection_reason
- create_date
- create_customer_id

company
- id
- company_name

card
- id
- name
- location_id
- company_id
- max_usage_time
- start_date
- end_date
- promotion
- create_date

customer
- id
- username
- password
- email
- account_status (APPROVED, SUSPENDED, DISABLED)
- role
- name
- picture
- sex
- address
- city
- phone
- bio
- registration_date
- disable_start_date
- disable_end_date
- disable_reason

customer_product
- id
- customer_id
- product_id
- start_period_id
- end_period_id

customer_payment
- id
- customer_id
- payment_type (CREDIT, ALIPAY)
- payment_account_id

customer_payment_transaction
- id
- customer_payment_id
- amount
- transaction_type
- transaction_date
- transaction_status
- transaction_error

customer_card
- id
- customer_id
- card_id
- location_id
- used_date

customer_checkin
- id
- customer_id
- location_id
- start_date
- end_date
- end_type (NO_SIGNAL, CHECK_OUT)
- is_approved

customer_profit
- id
- customer_id
- period_id
- amount

faq
- id
- question
- question_order
- answer

*/
drop table IF EXISTS customer           ;
-- drop table customer_product            ;
-- drop table customer_payment            ;
-- drop table customer_payment_transaction;
-- drop table customer_card               ;
-- drop table customer_checkin            ;
-- drop table customer_profit             ;
-- drop table product                     ;
-- drop table period                      ;
-- drop table location                    ;
-- drop table company                     ;
-- drop table card                        ;
-- drop table faq                         ;

CREATE TABLE customer (
  id integer AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(30) NOT NULL,
  password VARCHAR(15) NOT NULL,
  email VARCHAR(50) NOT NULL,
  account_status VARCHAR(50) NOT NULL,/*TRIAL, APPROVED, SUSPENDED, DISABLED*/
  role VARCHAR(30) default 'USER' NOT NULL,
  name VARCHAR(30),
  picture BLOB,
  sex VARCHAR(1),
  address VARCHAR(100),
  city VARCHAR(30),
  phone VARCHAR(30),
  bio VARCHAR(500),
  registration_date timestamp not null,
  disable_start_date timestamp,
  disable_end_date timestamp,
  disable_reason varchar(30)
);


-- ALTER TABLE customer ADD CONSTRAINT customer_company_fk
-- FOREIGN KEY (company_id)
-- REFERENCES company (id)
-- ON DELETE NO ACTION
-- ON UPDATE NO ACTION;

commit;