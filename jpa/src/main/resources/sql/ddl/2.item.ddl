CREATE TABLE item
(
    item_id NUMBER(255) NOT NULL UNIQUE AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL UNIQUE,
    price DECIMAL(10,2) NOT NULL,
    quantity NUMBER(255) NOT NULL,
    kind TINYINT NOT NULL, -- 0 = Etc, 1 = Movie, 2 = Album, 3 = Book, 4 = Drawing
    author VARCHAR(30),
    actor VARCHAR(30),
    director VARCHAR(30),
    artist VARCHAR(30),
    painter VARCHAR(30),
    description VARCHAR(255),
    PRIMARY KEY (item_id)
);