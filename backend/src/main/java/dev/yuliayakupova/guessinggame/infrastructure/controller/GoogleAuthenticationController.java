package dev.yuliayakupova.guessinggame.infrastructure.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import dev.yuliayakupova.guessinggame.application.dto.GameDTO;
import dev.yuliayakupova.guessinggame.application.dto.GoogleAuthResponse;
import dev.yuliayakupova.guessinggame.application.dto.PlayerDTO;
import dev.yuliayakupova.guessinggame.application.repository.GameRepository;
import dev.yuliayakupova.guessinggame.application.repository.PlayerRepository;
import dev.yuliayakupova.guessinggame.domain.value.SecretNumber;
import dev.yuliayakupova.guessinggame.infrastructure.request.TokenRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class GoogleAuthenticationController {

    private static final String CLIENT_ID = "125055213237-a87mu47e3u71vicmps3bkvt1lqpt5hie.apps.googleusercontent.com";

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    public GoogleAuthenticationController(PlayerRepository playerRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/auth/google")
    public ResponseEntity<?> googleLogin(@RequestBody TokenRequest tokenRequest) {
        String idTokenString = tokenRequest.getToken();
        if (idTokenString == null || idTokenString.isEmpty()) {
            return ResponseEntity.badRequest().body("Token must not be empty");
        }

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                return ResponseEntity.badRequest().body("Invalid ID token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            String email = payload.getEmail();
            String name = (String) payload.get("name");
            if (email == null || email.isEmpty()) {
                return ResponseEntity.badRequest().body("Email not found in token");
            }

            PlayerDTO player = playerRepository.findByEmail(email);
            if (player == null) {
                String playerName = (name != null && !name.isEmpty()) ? name : email;
                playerRepository.addPlayer(playerName, email);
                player = playerRepository.findByName(playerName);
            }

            SecretNumber secretNumber = new SecretNumber();
            GameDTO game = gameRepository.createNewGame(player.getId(), secretNumber.getSecret());

            return ResponseEntity.ok(new GoogleAuthResponse(player, game.getId()));

        } catch (GeneralSecurityException | IOException e) {
            return ResponseEntity.status(500).body("Failed to verify token: " + e.getMessage());
        }
    }

}
