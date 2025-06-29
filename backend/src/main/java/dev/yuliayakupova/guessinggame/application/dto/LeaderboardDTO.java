package dev.yuliayakupova.guessinggame.application.dto;

public class LeaderboardDTO {
    private String playerName;
    private int totalGames;
    private int gamesWon;
    private int totalTries;
    private double successRate;

    public LeaderboardDTO(String playerName, int totalGames, int gamesWon, int totalTries, double successRate) {
        this.playerName = playerName;
        this.totalGames = totalGames;
        this.gamesWon = gamesWon;
        this.totalTries = totalTries;
        this.successRate = successRate;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getTotalTries() {
        return totalTries;
    }

    public void setTotalTries(int totalTries) {
        this.totalTries = totalTries;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }
}
