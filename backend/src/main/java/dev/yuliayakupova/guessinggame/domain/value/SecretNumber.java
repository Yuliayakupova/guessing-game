package dev.yuliayakupova.guessinggame.domain.value;

import dev.yuliayakupova.guessinggame.application.dto.GameDTO;

import java.util.HashSet;
import java.util.Set;

public class SecretNumber {

    private final String secret;

    public SecretNumber(){
        Set<Integer> digits = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 4) {
            int d = (int) (Math.random() * 10);
            if (digits.add(d)) sb.append(d);
        }

        secret = sb.toString();
    }

    public SecretNumber(GameDTO game){
        this.secret = game.getSecretNumber();
    }

    public String getSecret() {
        return secret;
    }

    public char charAt(int i){
        return secret.charAt(i);
    }
}
