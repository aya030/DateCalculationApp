DROP TABLE IF EXISTS DateTable;

CREATE TABLE IF NOT EXISTS DateTable (
    id INT(50) PRIMARY KEY AUTO_INCREMENT,
    dateid VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    plusyear INT(50) NOT NULL,
    plusmonth INT(50) NOT NULL,
    plusday INT(50) NOT NULL
    );

