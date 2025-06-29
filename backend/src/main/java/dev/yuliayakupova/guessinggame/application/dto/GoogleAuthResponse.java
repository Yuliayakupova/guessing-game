package dev.yuliayakupova.guessinggame.application.dto;

public class GoogleAuthResponse {
    private PlayerDTO player;
    private Long gameId;

    public GoogleAuthResponse(PlayerDTO player, Long gameId) {
        this.player = player;
        this.gameId = gameId;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public Long getGameId() {
        return gameId;
    }
}
