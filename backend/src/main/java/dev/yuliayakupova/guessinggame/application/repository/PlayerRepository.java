package dev.yuliayakupova.guessinggame.application.repository;

import dev.yuliayakupova.guessinggame.application.dto.LeaderboardDTO;
import dev.yuliayakupova.guessinggame.application.dto.PlayerDTO;
import dev.yuliayakupova.guessinggame.application.service.SqlLoaderService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerRepository {

    private final JdbcTemplate jdbcTemplate;

    public PlayerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<LeaderboardDTO> getLeaderboard(int minGames) {
        String sql = SqlLoaderService.load("get_leaderboard.sql");
        return jdbcTemplate.query(sql, (rs, rowNum) -> new LeaderboardDTO(
                rs.getString("player_name"),
                rs.getInt("total_games"),
                rs.getInt("games_won"),
                rs.getInt("total_tries"),
                rs.getDouble("success_rate")
        ), minGames);
    }

    public boolean existsByName(String name) {
        String sql = SqlLoaderService.load("exists_player_by_name.sql");
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name);
        return count != null && count > 0;
    }

    public boolean existsByEmail(String email) {
        if (email == null) return false;
        String sql = SqlLoaderService.load("exists_player_by_email.sql");
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public PlayerDTO findByName(String name) {
        String sql = SqlLoaderService.load("get_player_by_name.sql");
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new PlayerDTO(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("total_games"),
                    rs.getInt("games_won"),
                    rs.getInt("total_tries")
            ), name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public PlayerDTO findByEmail(String email) {
        String sql = SqlLoaderService.load("get_player_by_email.sql");
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new PlayerDTO(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("total_games"),
                    rs.getInt("games_won"),
                    rs.getInt("total_tries")
            ), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addPlayer(String name, String email) {
        if (email != null && existsByEmail(email)) {
            throw new IllegalArgumentException("Player email already exists");
        }
        if (existsByName(name)) {
            throw new IllegalArgumentException("Player name already exists");
        }
        String sql = SqlLoaderService.load("insert_player_with_email.sql");
        jdbcTemplate.update(sql, name, email);
    }

}
