-- Create the user_db database
CREATE DATABASE IF NOT EXISTS user_db;

-- Create the user userdb_admin with password userdb_admin
CREATE USER IF NOT EXISTS 'userdb_admin'@'localhost' IDENTIFIED BY 'userdb_admin';

-- Grant all privileges on the user_db database to userdb_admin
GRANT ALL PRIVILEGES ON user_db.* TO 'userdb_admin'@'localhost';

-- Apply the changes
FLUSH PRIVILEGES;
