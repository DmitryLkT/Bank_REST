package org.lukdt.bank_card_management.util.encryptionServiceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lukdt.bank_card_management.util.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = {EncryptionService.class})
@TestPropertySource(properties = {"app.encryption.key=Bar12345Bar12345"})
public class DecryptionTest {
    @Autowired
    private EncryptionService encryptionService;
    private static final String DATA = "test_data";
    private static final String ENCRYPTED_DATA = "YV7zldXcHGvX+yIPDJsW7w==";

    @Test
    void validEncryptedData() {
        String encrypted = encryptionService.encrypt(DATA);
        String decrypted = encryptionService.decryption(encrypted);

        Assertions.assertEquals(DATA, decrypted);
    }

    @Test
    void withPreEncryptedData() {
        String decrypted = encryptionService.decryption(ENCRYPTED_DATA);

        Assertions.assertEquals(DATA, decrypted);
    }

    @Test
    void handleEmptyString() {
        String decrypted = encryptionService.decryption("");

        Assertions.assertEquals("", decrypted);
    }

    @Test
    void dataIsNotBase64() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> encryptionService.decryption("test"));

        Assertions.assertEquals("Invalid decryption data format", ex.getMessage());
    }

    @Test
    void dataIsCorrupted() {
        String encrypted = encryptionService.encrypt(DATA);
        String corrupted = encrypted.substring(0, encrypted.length() - 2) + "!!";

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> encryptionService.decryption(corrupted));
    }
}
