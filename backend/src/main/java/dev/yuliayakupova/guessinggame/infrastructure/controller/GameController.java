package dev.yuliayakupova.guessinggame.infrastructure.controller;

import dev.yuliayakupova.guessinggame.application.dto.GameDTO;
import dev.yuliayakupova.guessinggame.application.dto.GuessDTO;
import dev.yuliayakupova.guessinggame.application.dto.GuessResultDTO;
import dev.yuliayakupova.guessinggame.application.repository.GameRepository;
import dev.yuliayakupova.guessinggame.application.repository.GuessRepository;
import dev.yuliayakupova.guessinggame.domain.useCase.GameRole;
import dev.yuliayakupova.guessinggame.domain.value.Guess;
import dev.yuliayakupova.guessinggame.domain.value.SecretNumber;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin
public class GameController {

    private final GameRepository gameRepository;
    private final GuessRepository guessRepository;

    public GameController(GameRepository gameRepository, GuessRepository guessRepository) {
        this.gameRepository = gameRepository;
        this.guessRepository = guessRepository;
    }

    @PostMapping("/start")
    public GameDTO startGame(@RequestParam Long playerId) {
        SecretNumber secretNumber = new SecretNumber();
        return gameRepository.createNewGame(playerId, secretNumber.getSecret());
    }

    @GetMapping("/{gameId}")
    public GameDTO getGame(@PathVariable Long gameId) {
        return gameRepository.findById(gameId);
    }

    @PostMapping("/{gameId}/guess")
    public GuessResultDTO makeGuess(@PathVariable Long gameId, @RequestBody String guessInput) {
        Guess guess = new Guess(guessInput);

        GameDTO game = gameRepository.findById(gameId);
        if (game.isFinished()) {
            throw new IllegalStateException("Game already finished");
        }
        SecretNumber secret = new SecretNumber(game);

        GameRole role = new GameRole();
        int pCount = role.countExactMatches(secret, guess);
        int mCount = role.countMisplacedMatches(secret, guess);

        boolean won = pCount == 4;
        int triesLeft = game.getTriesLeft() - 1;
        boolean finished = won || triesLeft == 0;

        gameRepository.updateGame(gameId, triesLeft, finished, won);

        GuessDTO savedGuess = guessRepository.saveGuess(gameId, guessInput, mCount, pCount);

        String secretNumber = finished ? game.getSecretNumber() : null;

        return new GuessResultDTO(savedGuess, triesLeft, finished, won, secretNumber);
    }
}
