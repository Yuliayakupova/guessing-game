CREATE TABLE player (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE,
    total_games INT DEFAULT 0,
    games_won INT DEFAULT 0,
    total_tries INT DEFAULT 0
);

CREATE TABLE game (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    player_id BIGINT NOT NULL,
    secret_number CHAR(4) NOT NULL,
    tries_left INT NOT NULL,
    finished BOOLEAN DEFAULT FALSE,
    won BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (player_id) REFERENCES player(id)
);

CREATE TABLE guess (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    game_id BIGINT NOT NULL,
    guess_input CHAR(4) NOT NULL,
    m_count INT NOT NULL,
    p_count INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (game_id) REFERENCES game(id)
);
