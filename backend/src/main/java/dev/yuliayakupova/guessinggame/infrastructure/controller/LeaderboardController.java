package dev.yuliayakupova.guessinggame.infrastructure.controller;

import dev.yuliayakupova.guessinggame.application.dto.LeaderboardDTO;
import dev.yuliayakupova.guessinggame.application.repository.PlayerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@CrossOrigin
public class LeaderboardController {

    private final PlayerRepository playerRepository;

    public LeaderboardController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping
    public List<LeaderboardDTO> getLeaderboard(@RequestParam(defaultValue = "1") int minGames) {
        return playerRepository.getLeaderboard(minGames);
    }
}
