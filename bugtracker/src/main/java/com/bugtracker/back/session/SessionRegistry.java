package com.bugtracker.back.session;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

@Component
@AllArgsConstructor
public class SessionRegistry {
    private static final HashMap<String, String> SESSIONS = new HashMap<>();

    // private final ValueOperations<String, String> redisSessionStorage;

    public String registerSession(final String username) {
        if (username == null) {
            throw new RuntimeException("Username needs to be provided");
        }

        final String sessionId = generateSessionId();
        SESSIONS.put(sessionId, username);
        /*
         * try {
         * redisSessionStorage.set(sessionId, username);
         * } catch (final Exception e) {
         * e.printStackTrace();
         * SESSIONS.put(sessionId, username);
         * }
         */
        return sessionId;
    }

    public String getUsernameForSession(final String sessionId) {
        return SESSIONS.get(sessionId);
        /*
         * try {
         * return redisSessionStorage.get(sessionId);
         * } catch (final Exception e) {
         * e.printStackTrace();
         * return SESSIONS.get(sessionId);
         * }
         */
    }
    private String generateSessionId() {
        return new String(
                Base64.getEncoder().encode(
                        UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)));
    }
}