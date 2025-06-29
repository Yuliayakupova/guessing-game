package dev.yuliayakupova.guessinggame;

import dev.yuliayakupova.guessinggame.application.dto.GameDTO;
import dev.yuliayakupova.guessinggame.domain.useCase.GameRole;
import dev.yuliayakupova.guessinggame.domain.value.Guess;
import dev.yuliayakupova.guessinggame.domain.value.SecretNumber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameRoleUnitTest {

    private SecretNumber createSecretFromString(String secretStr) {
        GameDTO gameDto = new GameDTO();
        gameDto.setSecretNumber(secretStr);
        return new SecretNumber(gameDto);
    }

    @Test
    public void testCountExactMatches() {
        SecretNumber secret = createSecretFromString("1234");
        Guess guess = new Guess("1534");
        GameRole gameRole = new GameRole();

        int exact = gameRole.countExactMatches(secret, guess);
        assertEquals(3, exact);
    }

    @Test
    public void testCountMisplacedMatches() {
        SecretNumber secret = createSecretFromString("1234");
        Guess guess = new Guess("4321");
        GameRole gameRole = new GameRole();

        int misplaced = gameRole.countMisplacedMatches(secret, guess);
        assertEquals(4, misplaced);
    }

    @Test
    public void testExactAndMisplacedMatchesTogether() {
        SecretNumber secret = createSecretFromString("1234");
        Guess guess = new Guess("1243");
        GameRole gameRole = new GameRole();

        int exact = gameRole.countExactMatches(secret, guess);
        int misplaced = gameRole.countMisplacedMatches(secret, guess);

        assertEquals(2, exact);
        assertEquals(2, misplaced);
    }
}
