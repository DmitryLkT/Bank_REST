package org.lukdt.bank_card_management.util.encryptionServiceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lukdt.bank_card_management.util.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Base64;

@SpringBootTest(classes = {EncryptionService.class})
@TestPropertySource(properties = {"app.encryption.key=Bar12345Bar12345"})
public class EncryptionTest {
    @Autowired
    private EncryptionService encryptionService;
    private static final String DATA = "test_data";

    @Test
    void validInput() {
        String encrypted = encryptionService.encrypt(DATA);

        Assertions.assertNotNull(encrypted);
        Assertions.assertFalse(encrypted.isEmpty());
        Assertions.assertDoesNotThrow(() -> Base64.getDecoder().decode(encrypted));
    }

    @Test
    void forDifferentInputs() {
        String encrypted1 = encryptionService.encrypt("test_data1");
        String encrypted2 = encryptionService.encrypt("test_data2");

        Assertions.assertNotEquals(encrypted1, encrypted2);
    }

    @Test
    void forSameInput() {
        String encrypted1 = encryptionService.encrypt(DATA);
        String encrypted2 = encryptionService.encrypt(DATA);

        Assertions.assertEquals(encrypted1.length(), encrypted2.length());
    }


}
