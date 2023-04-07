package Maswillaeng.MSLback.Util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordEncoder {

    @Value("${security.salt}")
    private String SALT;

    public String encode(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] saltBytes = SALT.getBytes(StandardCharsets.UTF_8);
            md.update(saltBytes);
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }
}