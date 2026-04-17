package org.lukdt.bank_card_management.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionService {
    private static final Logger log = LoggerFactory.getLogger(EncryptionService.class);
    private static final String ALGORITHM = "AES";

    @Value("${APP_ENCRYPTION_KEY}")
    private static String secretKey;

    public static String encrypt(String data) {
        try {
            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] bytes = cipher.doFinal(data.getBytes());

            return Base64.getEncoder().encodeToString(bytes);
        } catch (InvalidKeyException e) {
            log.error("Invalid AES key provided. Check key length (must be 16, 24 or 32 bytes)", e);
            throw new SecurityException("Encryption configuration error", e);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            log.error("Data corruption or wrong padding during crypto operation", e);
            throw new IllegalArgumentException("Invalid encrypted data format", e);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("Algorithm AES not available in this JVM", e);
            throw new IllegalStateException("Encryption is not supported on this platform.", e);
        }
    }

    public static String decryption(String data) {
        try {
            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(data));

            return new String(bytes);
        } catch (InvalidKeyException e) {
            log.error("Invalid AES key provided. Check key length (must be 16, 24 or 32 bytes)", e);
            throw new SecurityException("Decryption configuration error", e);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            log.error("Data corruption or wrong padding during crypto operation", e);
            throw new IllegalArgumentException("Invalid decryption data format", e);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("Algorithm AES not available in this JVM", e);
            throw new IllegalStateException("Decryption is not supported on this platform.", e);
        }
    }
}
