package Maswillaeng.MSLback.Util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class AESUtil {


    private SecretKey secretKey;
    private byte[] IV;

    public AESUtil(@Value("${secret.access}") String secretAccessKey) {
        try {
            byte[] keyBytes = secretAccessKey.getBytes(StandardCharsets.UTF_8);
            secretKey = new SecretKeySpec(keyBytes, "AES");

            // 생성된 IV 값을 저장
            IV = new byte[16];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(IV);
        } catch (Exception e) {
            throw new RuntimeException("Error generating AES secret key", e);
        }
    }

    public String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(IV));
            byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting string", e);
        }
    }

    public String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(IV));
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(strToDecrypt));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting string", e);
        }
    }
}