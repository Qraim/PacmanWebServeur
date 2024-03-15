-- Creating the users table
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255),
                       email VARCHAR(255) UNIQUE,
                       password VARCHAR(255)
);

-- Creating the games table
CREATE TABLE games (
                       id SERIAL PRIMARY KEY,
                       map TEXT,
                       score INT,
                       status VARCHAR(255)
);

-- Creating the user_games table
CREATE TABLE user_games (
                            user_id INT,
                            game_id INT,
                            score, INT,
                            FOREIGN KEY (user_id) REFERENCES users(id),
                            FOREIGN KEY (game_id) REFERENCES games(id)
);
