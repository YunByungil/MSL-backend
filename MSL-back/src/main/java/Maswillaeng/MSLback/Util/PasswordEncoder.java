package Maswillaeng.MSLback.Util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordEncoder {

//    @Value("${security.salt}")
    private String SALT = "maswillaengproject";

    public String encode(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            System.out.println("md = " + md);
            byte[] saltBytes = SALT.getBytes(StandardCharsets.UTF_8);
            md.update(saltBytes);
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            System.out.println("hash = " + hash);
            StringBuilder sb = new StringBuilder();
            System.out.println("sb = " + sb);
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            System.out.println("hashsb = " + sb);
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }
}
