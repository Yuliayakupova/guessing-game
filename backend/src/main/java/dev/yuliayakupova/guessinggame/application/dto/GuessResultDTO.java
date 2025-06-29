package dev.yuliayakupova.guessinggame.application.dto;

public class GuessResultDTO {
    private GuessDTO guess;
    private int triesLeft;
    private boolean finished;
    private boolean won;
    private String secretNumber;

    public GuessResultDTO(GuessDTO guess, int triesLeft, boolean finished, boolean won, String secretNumber) {
        this.guess = guess;
        this.triesLeft = triesLeft;
        this.finished = finished;
        this.won = won;
        this.secretNumber = secretNumber;
    }

    public GuessDTO getGuess() {
        return guess;
    }

    public void setGuess(GuessDTO guess) {
        this.guess = guess;
    }

    public int getTriesLeft() {
        return triesLeft;
    }

    public void setTriesLeft(int triesLeft) {
        this.triesLeft = triesLeft;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public String getSecretNumber() {
        return secretNumber;
    }

    public void setSecretNumber(String secretNumber) {
        this.secretNumber = secretNumber;
    }
}

