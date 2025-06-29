package dev.yuliayakupova.guessinggame;

import dev.yuliayakupova.guessinggame.domain.exception.GuessException;
import dev.yuliayakupova.guessinggame.domain.value.Guess;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuessUnitTest {
    @Test
    public void testValidGuess() {
        Guess guess = new Guess("1234");
        assertEquals("1234", guess.getInput());
        assertEquals('1', guess.charAt(0));
        assertEquals('2', guess.charAt(1));
        assertEquals('3', guess.charAt(2));
        assertEquals('4', guess.charAt(3));
    }

    @Test
    public void testGuessWithInvalidLength() {
        Exception ex1 = assertThrows(GuessException.class, () -> new Guess("123"));
        assertTrue(ex1.getMessage().contains("The number must consist of exactly 4 digits (0–9). No letters or symbols allowed."));

        Exception ex2 = assertThrows(GuessException.class, () -> new Guess("12345"));
        assertTrue(ex2.getMessage().contains("The number must consist of exactly 4 digits (0–9). No letters or symbols allowed."));
    }

    @Test
    public void testGuessWithNonDigitCharacters() {
        Exception ex = assertThrows(GuessException.class, () -> new Guess("12a4"));
        assertTrue(ex.getMessage().contains("The number must consist of exactly 4 digits (0–9). No letters or symbols allowed."));
    }

    @Test
    public void testGuessWithDuplicateDigits() {
        Exception ex = assertThrows(GuessException.class, () -> new Guess("1123"));
        assertTrue(ex.getMessage().contains("All digits must be unique. Duplicate digits are not allowed."));
    }

    @Test
    public void testNullInput() {
        assertThrows(GuessException.class, () -> new Guess(null));
    }
}
