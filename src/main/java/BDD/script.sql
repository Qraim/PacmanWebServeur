CREATE TABLE Users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE Games (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       map VARCHAR(255) NOT NULL,
                       score INT NOT NULL,
                       status VARCHAR(255) NOT NULL,
                       joueur_id INT,
                       FOREIGN KEY (joueur_id) REFERENCES Users(id)
);

CREATE TABLE UserGames (
                           user_id INT,
                           game_id INT,
                           PRIMARY KEY (user_id, game_id),
                           FOREIGN KEY (user_id) REFERENCES Users(id),
                           FOREIGN KEY (game_id) REFERENCES Games(id)
);
