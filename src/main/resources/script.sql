CREATE DATABASE IF NOT EXISTS Restaurants;
USE Restaurants;

-- Restaurant table
CREATE TABLE IF NOT EXISTS restaurant (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    description VARCHAR(255),
    openTime VARCHAR(100),
    closeTime VARCHAR(100),
    address VARCHAR(100),
    phoneNumber VARCHAR(20),
    images VARCHAR(255),
    status VARCHAR(15) DEFAULT('active'),
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- Users table with authentication fields
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(20) NOT NULL,
    address VARCHAR(250),
    phoneNumber VARCHAR(20),
    position VARCHAR(50),
    NIC VARCHAR(15),
    status VARCHAR(15) DEFAULT('active'),
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    accountType ENUM('Admin', 'Staff', 'Customer')
    );

-- Menu items table
CREATE TABLE IF NOT EXISTS menuItems (
    id INT PRIMARY KEY AUTO_INCREMENT,
    itemName VARCHAR(50),
    price DECIMAL(10, 2)
    );

-- Offers table
CREATE TABLE IF NOT EXISTS offers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    offerName VARCHAR(50),
    promoCode VARCHAR(20),
    discountPercentage DECIMAL(5, 2),
    category VARCHAR(100) NULL,
    imagePath VARCHAR(255) NULL,
    status VARCHAR(15) DEFAULT('active'),
    CreatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    );

-- Reservations table
CREATE TABLE IF NOT EXISTS reservations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    offerId INT,
    date DATE,
    time TIME,
    customerId INT,
    restaurantId INT,
    FOREIGN KEY (offerId) REFERENCES offers(id),
    FOREIGN KEY (customerId) REFERENCES users(id),
    FOREIGN KEY (restaurantId) REFERENCES restaurant(id)
    );

-- Orders table
CREATE TABLE IF NOT EXISTS orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    date DATE,
    time TIME,
    totalAmount DECIMAL(10, 2),
    customerId INT,
    restaurantId INT,
    FOREIGN KEY (customerId) REFERENCES users(id),
    FOREIGN KEY (restaurantId) REFERENCES restaurant(id)
    );

-- Order Items junction table
CREATE TABLE IF NOT EXISTS order_items (
    orderId INT,
    menuItemId INT,
    quantity INT,
    FOREIGN KEY (orderId) REFERENCES orders(id),
    FOREIGN KEY (menuItemId) REFERENCES menuItems(id),
    PRIMARY KEY (orderId, menuItemId)
    );

-- Queries table
CREATE TABLE IF NOT EXISTS queries (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50),
    description TEXT,
    response TEXT,
    customerId INT,
    status ENUM('Open', 'Closed', 'Pending'),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customerId) REFERENCES users(id)
    );

-- Payments table
CREATE TABLE IF NOT EXISTS payments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    orderId INT,
    paymentMethod ENUM('Credit Card', 'Debit Card', 'Cash', 'Online'),
    paymentStatus ENUM('Pending', 'Completed', 'Failed'),
    amount DECIMAL(10, 2),
    paymentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (orderId) REFERENCES orders(id)
    );

-- Facility table
CREATE TABLE IF NOT EXISTS Facilities (
    id INT PRIMARY KEY AUTO_INCREMENT,
    restaurantID INT,
    name VARCHAR(255) NOT NULL,
    description TEXT NULL,
    category VARCHAR(100) NULL,
    imagePath VARCHAR(255) NULL,
    status VARCHAR(15) DEFAULT('active'),
    CreatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (RestaurantID) REFERENCES Restaurants(id)
);

-- Gallery table
CREATE TABLE IF NOT EXISTS Gallery (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT NULL,
    category VARCHAR(100) NULL,
    imagePath VARCHAR(255) NULL,
    status VARCHAR(15) DEFAULT('active'),
    CreatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
);

-- Products table
CREATE TABLE IF NOT EXISTS Products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT NULL,
    category VARCHAR(100) NULL,
    imagePath VARCHAR(255) NULL,
    price DECIMAL(10, 2),
    status VARCHAR(15) DEFAULT('active'),
    CreatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );
