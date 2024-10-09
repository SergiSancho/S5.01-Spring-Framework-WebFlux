CREATE DATABASE blackjack_db;

USE blackjack_db;

CREATE TABLE players (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255),
    guanyades INT,
    perdudes INT,
    empatades INT
);
