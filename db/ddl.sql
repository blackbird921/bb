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
