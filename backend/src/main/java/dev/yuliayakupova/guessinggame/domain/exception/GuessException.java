package dev.yuliayakupova.guessinggame.domain.exception;

public class GuessException extends IllegalArgumentException {

    public GuessException(String message){
        super(message);
    }

    public static GuessException invalidNumberLength(){
        return new GuessException("The number must consist of exactly 4 digits (0–9). No letters or symbols allowed.");
    }

    public static GuessException duplicateDigits(){
        return new GuessException("All digits must be unique. Duplicate digits are not allowed.");
    }
}
