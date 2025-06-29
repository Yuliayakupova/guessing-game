package dev.yuliayakupova.guessinggame.domain.useCase;

import dev.yuliayakupova.guessinggame.domain.value.Guess;
import dev.yuliayakupova.guessinggame.domain.value.SecretNumber;

public class GameRole {

    public int countExactMatches(SecretNumber secret, Guess guess) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            if (secret.charAt(i) == guess.charAt(i)) count++;
        }
        return count;
    }

    public int countMisplacedMatches(SecretNumber secret, Guess guess) {
        int[] sFreq = new int[10];
        int[] gFreq = new int[10];

        for (int i = 0; i < 4; i++) {
            if (secret.charAt(i) != guess.charAt(i)) {
                sFreq[secret.charAt(i) - '0']++;
                gFreq[guess.charAt(i) - '0']++;
            }
        }

        int count = 0;
        for (int i = 0; i < 10; i++) {
            count += Math.min(sFreq[i], gFreq[i]);
        }

        return count;
    }
}
