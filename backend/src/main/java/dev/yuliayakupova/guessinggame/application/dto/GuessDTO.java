package dev.yuliayakupova.guessinggame.application.dto;

import java.time.LocalDateTime;

public class GuessDTO {
    private Long id;
    private Long gameId;
    private String guessInput;
    private int mCount;
    private int pCount;
    private LocalDateTime createdAt;


    public GuessDTO(Long id, Long gameId, String guessInput, int mCount, int pCount, LocalDateTime createdAt) {
        this.id = id;
        this.gameId = gameId;
        this.guessInput = guessInput;
        this.mCount = mCount;
        this.pCount = pCount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGuessInput() {
        return guessInput;
    }

    public void setGuessInput(String guessInput) {
        this.guessInput = guessInput;
    }

    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }

    public int getpCount() {
        return pCount;
    }

    public void setpCount(int pCount) {
        this.pCount = pCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
