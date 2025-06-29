package dev.yuliayakupova.guessinggame.application.repository;

import dev.yuliayakupova.guessinggame.application.dto.GameDTO;
import dev.yuliayakupova.guessinggame.application.service.SqlLoaderService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class GameRepository {

    private final JdbcTemplate jdbc;

    public GameRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public GameDTO createNewGame(Long playerId, String secretNumber) {
        String sql = SqlLoaderService.load("insert_game.sql");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, playerId);
            ps.setString(2, secretNumber);
            ps.setInt(3, 8);
            return ps;
        }, keyHolder);

        var keys = keyHolder.getKeys();
        if (keys == null || keys.isEmpty()) {
            throw new IllegalStateException("No keys returned after insert");
        }

        Number idNumber = (Number) keys.get("ID");
        if (idNumber == null) {
            throw new IllegalStateException("Key 'ID' not found in returned keys");
        }

        Long id = idNumber.longValue();

        return findById(id);
    }

    public GameDTO findById(Long id) {
        String sql = SqlLoaderService.load("select_game_by_id.sql");
        return jdbc.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> new GameDTO(
                rs.getLong("id"),
                rs.getLong("player_id"),
                rs.getString("secret_number"),
                rs.getInt("tries_left"),
                rs.getBoolean("finished"),
                rs.getBoolean("won"),
                rs.getTimestamp("created_at").toLocalDateTime()
        ));
    }

    public void updateGame(Long gameId, int triesLeft, boolean finished, boolean won) {
        String sql = SqlLoaderService.load("update_game.sql");
        jdbc.update(sql, triesLeft, finished, won, gameId);
    }
}

