-- Create the user
CREATE USER 'czabaladev' IDENTIFIED BY 'rukia1225dev';

-- Create the schema
CREATE SCHEMA myhome;

-- Grant privileges to the user on the schema
GRANT ALL PRIVILEGES ON myhome.* TO 'czabaladev';

-- Flush privileges to apply changes
FLUSH PRIVILEGES;
