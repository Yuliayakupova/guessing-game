package dev.yuliayakupova.guessinggame.domain.value;

import dev.yuliayakupova.guessinggame.domain.exception.GuessException;

import java.util.HashSet;
import java.util.Set;

public class Guess {

    private final String input;

    public Guess(String input){
        if (input == null || input.length() != 4 || !input.matches("\\d{4}")) {
            throw GuessException.invalidNumberLength();
        }

        Set<Character> unique = new HashSet<>();
        for (char c : input.toCharArray()) {
            if (!unique.add(c)) {
                throw GuessException.duplicateDigits();
            }
        }
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public char charAt(int i){
        return input.charAt(i);
    }
}
