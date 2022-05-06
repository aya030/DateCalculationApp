DROP TABLE IF EXISTS calculation_dates;

CREATE TABLE IF NOT EXISTS calculation_dates (
    id INT(50) PRIMARY KEY AUTO_INCREMENT,
    date_id VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    plus_year INT(50) NOT NULL,
    plus_month INT(50) NOT NULL,
    plus_day INT(50) NOT NULL
);

