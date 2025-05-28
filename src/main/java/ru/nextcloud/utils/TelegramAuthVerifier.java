package ru.nextcloud.utils;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

public class TelegramAuthVerifier {

    public static boolean isValid(Map<String, String> data, String botToken) {
        String receivedHash = data.get("hash");

        Map<String, String> dataCheckMap = new HashMap<>(data);
        dataCheckMap.remove("hash");

        // Sort keys
        List<String> keys = new ArrayList<>(dataCheckMap.keySet());
        Collections.sort(keys);

        // Build data-check-string
        StringBuilder dataCheckString = new StringBuilder();
        for (String key : keys) {
            dataCheckString.append(key).append("=").append(dataCheckMap.get(key)).append("\n");
        }
        // Remove trailing newline
        if (dataCheckString.length() > 0) {
            dataCheckString.setLength(dataCheckString.length() - 1);
        }

        // Generate secret key
        byte[] secretKey = sha256(botToken.getBytes(StandardCharsets.UTF_8));

        // Compute HMAC-SHA256
        String computedHash = hmacSha256Hex(secretKey, dataCheckString.toString());

        // Compare
        return computedHash.equals(receivedHash);
    }

    private static byte[] sha256(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(data);
        } catch (Exception e) {
            throw new RuntimeException("Error calculating SHA-256", e);
        }
    }

    private static String hmacSha256Hex(byte[] key, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec spec = new SecretKeySpec(key, "HmacSHA256");
            mac.init(spec);
            byte[] hmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(hmac);
        } catch (Exception e) {
            throw new RuntimeException("Error calculating HMAC", e);
        }
    }
}
