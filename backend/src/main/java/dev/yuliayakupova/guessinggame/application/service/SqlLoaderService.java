package dev.yuliayakupova.guessinggame.application.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class SqlLoaderService {
    public static String load(String filename) {
        String resourcePath = "db/queries/" + filename;
        InputStream is = SqlLoaderService.class.getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new RuntimeException("SQL resource not found: " + resourcePath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load SQL resource: " + resourcePath, e);
        }
    }
}
