package com.orbital3d.web.security;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Cryptographic tools.
 */
public final class CryptoTool {
    private static final Random RANDOM = new SecureRandom();
    /**
     * Generate salt.
     * @return byte array of the generated salt
     */
    public static byte [] generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

}
