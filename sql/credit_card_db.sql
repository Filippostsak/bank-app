-- Create the credit_card_db database
CREATE DATABASE IF NOT EXISTS credit_card_db;

-- Create the user credit_card_admin with password credit_card_admin
CREATE USER IF NOT EXISTS 'credit_card_admin'@'localhost' IDENTIFIED BY 'credit_card_admin';

-- Grant all privileges on the credit_card_db database to credit_card_admin
GRANT ALL PRIVILEGES ON credit_card_db.* TO 'credit_card_admin'@'localhost';

-- Apply the changes
FLUSH PRIVILEGES;
