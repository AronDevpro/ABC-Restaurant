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
    capacity INT,
    image VARCHAR(255),
    status VARCHAR(15) DEFAULT('active'),
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- Users table
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
    UpdatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- Queries table
CREATE TABLE IF NOT EXISTS Queries (
    id INT PRIMARY KEY AUTO_INCREMENT,
    subject VARCHAR(50) NOT NULL,
    phoneNumber VARCHAR(20) NOT NULL,
    orderId VARCHAR(100) NULL,
    description TEXT NOT NULL,
    response TEXT NULL,
    customerId INT NOT NULL,
    staffId INT NULL,
    status ENUM('Answered', 'Closed', 'Pending') DEFAULT('Pending'),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customerId) REFERENCES users(id),
    FOREIGN KEY (staffId) REFERENCES users(id)
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
    UpdatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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

-- Orders table
CREATE TABLE IF NOT EXISTS Orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    orderUUID CHAR(36) NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phoneNumber VARCHAR(100) NOT NULL,
    deliverDate DATE NOT NULL,
    deliverTime TIME NOT NULL,
    deliveryMethod VARCHAR(30) NOT NULL,
    restaurantSelect VARCHAR(100) NULL,
    streetAddress VARCHAR(150) NULL,
    zip VARCHAR(50) NULL,
    city VARCHAR(50) NULL,
    total DECIMAL(10, 2),
    customerId INT,
    status VARCHAR(15) DEFAULT('pending'),
    CreatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customerId) REFERENCES users(id)
    );

-- Orders table
CREATE TABLE IF NOT EXISTS Transactions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    orderId INT NOT NULL,
    paymentMethod VARCHAR(255) NULL,
    total DECIMAL(10, 2),
    status VARCHAR(15) DEFAULT('success'),
    CreatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (orderId) REFERENCES orders(id)
    );

-- Order Items table
CREATE TABLE IF NOT EXISTS orderItems (
    orderId INT,
    item VARCHAR(50),
    quantity INT,
    FOREIGN KEY (orderId) REFERENCES orders(id),
    PRIMARY KEY (orderId, item)
    );

--  Reservation table
CREATE TABLE IF NOT EXISTS reservation (
    id INT PRIMARY KEY AUTO_INCREMENT,
    reservationId CHAR(36),
    customerId INT NOT NULL,
    restaurantId INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    phoneNumber VARCHAR(20) NOT NULL,
    date VARCHAR(20) NOT NULL,
    time VARCHAR(20) NOT NULL,
    noOfPeople INT NULL,
    status VARCHAR(30) DEFAULT('pending'),
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (restaurantId) REFERENCES restaurant(id),
    FOREIGN KEY (customerId) REFERENCES users(id)
    );

-- store procedure for check availability
DELIMITER //

CREATE PROCEDURE check_availability(
    IN p_restaurantId INT,
    IN p_date DATE,
    IN p_time TIME,
    OUT p_result INT,
    OUT p_message VARCHAR(255)
)
BEGIN
    DECLARE v_openTime TIME;
    DECLARE v_closeTime TIME;
    DECLARE v_capacity INT;
    DECLARE v_reservationCount INT;

SELECT openTime, closeTime, capacity INTO v_openTime, v_closeTime, v_capacity
FROM restaurant
WHERE id = p_restaurantId;

-- Check if the requested time is within operating hours
IF p_time < v_openTime OR p_time > v_closeTime THEN
        SET p_result = 0;
        SET p_message = 'The restaurant is closed during the selected time.';
ELSE
-- Check the number of reservations for the given date and time
SELECT COUNT(*) INTO v_reservationCount
FROM reservation
WHERE restaurantId = p_restaurantId
  AND date = p_date;

IF v_reservationCount >= v_capacity THEN
SET p_result = 0;
SET p_message = 'The restaurant is fully booked for the selected date.';
ELSE
SET p_result = 1;
SET p_message = 'The selected time is available for reservation.';
END IF;
END IF;
END //

DELIMITER ;

-- Settings table
CREATE TABLE IF NOT EXISTS Settings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    siteName VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    logoPath TEXT NOT NULL,
    siteEmail VARCHAR(255) NOT NULL,
    siteStreetAddress VARCHAR(255) NOT NULL,
    siteZip VARCHAR(255) NOT NULL,
    siteCity VARCHAR(255) NOT NULL,
    serverEmail VARCHAR(255) NOT NULL,
    serverPassword VARCHAR(255) NOT NULL,
    UpdatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );