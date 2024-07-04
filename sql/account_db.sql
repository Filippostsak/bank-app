-- Create the user_db database
CREATE DATABASE IF NOT EXISTS account_db;

-- Create the user userdb_admin with password userdb_admin
CREATE USER IF NOT EXISTS 'account_admin'@'localhost' IDENTIFIED BY 'account_admin';

-- Grant all privileges on the user_db database to userdb_admin
GRANT ALL PRIVILEGES ON account_db.* TO 'account_admin'@'localhost';

-- Apply the changes
FLUSH PRIVILEGES;
