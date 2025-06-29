package dev.yuliayakupova.guessinggame.application.dto;

import java.time.LocalDateTime;

public class GameDTO {
    private Long id;
    private Long playerId;
    private String secretNumber;
    private int triesLeft;
    private boolean finished;
    private boolean won;
    private LocalDateTime createdAt;

    public GameDTO(Long id, Long playerId, String secretNumber, int triesLeft, boolean finished, boolean won, LocalDateTime createdAt) {
        this.id = id;
        this.playerId = playerId;
        this.secretNumber = secretNumber;
        this.triesLeft = triesLeft;
        this.finished = finished;
        this.won = won;
        this.createdAt = createdAt;
    }

    public GameDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getSecretNumber() {
        return secretNumber;
    }

    public void setSecretNumber(String secretNumber) {
        this.secretNumber = secretNumber;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
