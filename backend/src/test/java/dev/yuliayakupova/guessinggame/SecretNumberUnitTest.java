package dev.yuliayakupova.guessinggame;

import dev.yuliayakupova.guessinggame.application.dto.GameDTO;
import dev.yuliayakupova.guessinggame.domain.value.SecretNumber;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SecretNumberUnitTest {

    @Test
    void generatedSecretNumberShouldBeFourDigits() {
        SecretNumber secretNumber = new SecretNumber();
        String secret = secretNumber.getSecret();

        assertEquals(4, secret.length(), "Secret number should be 4 digits long");
        assertTrue(secret.matches("\\d{4}"), "Secret number should contain only digits");
    }

    @Test
    void generatedSecretNumberShouldHaveUniqueDigits() {
        SecretNumber secretNumber = new SecretNumber();
        String secret = secretNumber.getSecret();

        Set<Character> uniqueDigits = new HashSet<>();
        for (char c : secret.toCharArray()) {
            uniqueDigits.add(c);
        }

        assertEquals(4, uniqueDigits.size(), "All digits in the secret number should be unique");
    }

    @Test
    void secretNumberFromGameDTOShouldMatch() {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setSecretNumber("1234");

        SecretNumber secretNumber = new SecretNumber(gameDTO);

        assertEquals("1234", secretNumber.getSecret(), "Secret number from GameDTO should be used");
    }

    @Test
    void charAtShouldReturnCorrectDigit() {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setSecretNumber("9876");

        SecretNumber secretNumber = new SecretNumber(gameDTO);

        assertEquals('9', secretNumber.charAt(0));
        assertEquals('8', secretNumber.charAt(1));
        assertEquals('7', secretNumber.charAt(2));
        assertEquals('6', secretNumber.charAt(3));
    }

    @Test
    void charAtShouldThrowExceptionForInvalidIndex() {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setSecretNumber("1234");
        SecretNumber secretNumber = new SecretNumber(gameDTO);

        assertThrows(IndexOutOfBoundsException.class, () -> secretNumber.charAt(4));
    }

}
