-- Whenever we want to create or alter a table, we need to create a schema.sql file
-- This file will be executed whenever the application is started

CREATE TABLE IF NOT EXISTS `customers` (
    `customer_id` int AUTO_INCREMENT  PRIMARY KEY,
    `name` varchar(100) NOT NULL,
    `email` varchar(100) NOT NULL,
    `mobile_number` varchar(20) NOT NULL,
    `created_at` date NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `accounts` (
    -- currently we never did any mapping between accounts and customers, but here the customer_id
    -- in the "accounts" table is work as a foreign key
    `customer_id` int NOT NULL,
    `account_number` int AUTO_INCREMENT  PRIMARY KEY,
    `account_type` varchar(100) NOT NULL,
    `branch_address` varchar(200) NOT NULL,
    `created_at` date NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);