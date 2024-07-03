-- Create the account database
CREATE DATABASE IF NOT EXISTS account;

-- Create the user account_admin with password account_admin
CREATE USER IF NOT EXISTS 'account_admin'@'localhost' IDENTIFIED BY 'account_admin';

-- Grant all privileges on the account database to account_admin
GRANT ALL PRIVILEGES ON account.* TO 'account_admin'@'localhost';

-- Apply the changes
FLUSH PRIVILEGES;
