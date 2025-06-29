package dev.yuliayakupova.guessinggame.infrastructure.controller;

import dev.yuliayakupova.guessinggame.application.dto.PlayerDTO;
import dev.yuliayakupova.guessinggame.application.repository.PlayerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PlayerController {

    private final PlayerRepository playerRepository;

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @PostMapping("/player")
    public ResponseEntity<?> createPlayer(@RequestBody PlayerDTO playerDto) {
        String name = playerDto.getName();
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Player name must not be empty");
        }

        try {
            playerRepository.addPlayer(name.trim(), null);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/player/{name}")
    public PlayerDTO getPlayer(@PathVariable String name) {
        return playerRepository.findByName(name);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
