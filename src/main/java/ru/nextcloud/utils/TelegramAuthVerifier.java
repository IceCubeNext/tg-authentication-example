package ru.nextcloud.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

@Slf4j
public class TelegramAuthVerifier {

    public static boolean isValid(Map<String, String> data, String botToken) {
        try {
            String receivedHash = data.remove("hash");
            String checkString = buildCheckString(data);

            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretSpec = new SecretKeySpec("WebAppData".getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSha256.init(secretSpec);
            byte[] secretKeyBytes = hmacSha256.doFinal(botToken.getBytes(StandardCharsets.UTF_8));

            SecretKeySpec keySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
            Mac hmac = Mac.getInstance("HmacSHA256");
            hmac.init(keySpec);
            byte[] hashBytes = hmac.doFinal(checkString.getBytes(StandardCharsets.UTF_8));
            String calculatedHash = bytesToHex(hashBytes);

            return calculatedHash.equals(receivedHash);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private static String buildCheckString(Map<String, String> data) {
        return data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("\n"));
    }

    private static String bytesToHex(byte[] bytes) {
        return Hex.encodeHexString(bytes);
    }
}
