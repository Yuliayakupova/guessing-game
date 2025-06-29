package dev.yuliayakupova.guessinggame.application.dto;


public class PlayerDTO {
    private Long id;
    private String name;
    private String email;
    private int totalGames;
    private int gamesWon;
    private int totalTries;

    public PlayerDTO(Long id, String name, String email, int totalGames, int gamesWon, int totalTries) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.totalGames = totalGames;
        this.gamesWon = gamesWon;
        this.totalTries = totalTries;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
