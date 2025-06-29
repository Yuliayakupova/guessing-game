package dev.yuliayakupova.guessinggame.application.repository;

import dev.yuliayakupova.guessinggame.application.dto.GuessDTO;
import dev.yuliayakupova.guessinggame.application.service.SqlLoaderService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class GuessRepository {

    private final JdbcTemplate jdbc;

    public GuessRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public GuessDTO saveGuess(Long gameId, String guessInput, int mCount, int pCount) {
        String sql = SqlLoaderService.load("insert_guess.sql");
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, gameId);
            ps.setString(2, guessInput);
            ps.setInt(3, mCount);
            ps.setInt(4, pCount);
            return ps;
        }, keyHolder);

        var keys = keyHolder.getKeys();
        Long id = ((Number) keys.get("ID")).longValue();

        return findById(id);
    }

    public GuessDTO findById(Long id) {
        String sql = SqlLoaderService.load("select_guess_by_id.sql");
        return jdbc.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> new GuessDTO(
                rs.getLong("id"),
                rs.getLong("game_id"),
                rs.getString("guess_input"),
                rs.getInt("m_count"),
                rs.getInt("p_count"),
                rs.getTimestamp("created_at").toLocalDateTime()
        ));
    }

    public List<GuessDTO> findByGameId(Long gameId) {
        String sql = SqlLoaderService.load("guess/select_guesses_by_game_id.sql");
        return jdbc.query(sql, new Object[]{gameId}, (rs, rowNum) -> new GuessDTO(
                rs.getLong("id"),
                rs.getLong("game_id"),
                rs.getString("guess_input"),
                rs.getInt("m_count"),
                rs.getInt("p_count"),
                rs.getTimestamp("created_at").toLocalDateTime()
        ));
    }
}
