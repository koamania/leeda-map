package ga.leeda.map.user.application.service.imp;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PasswordEncryptorTest {

    @Test
    public void password_encryption_test() {
        String input = "dhlee_password";
        String encryptedPassword = PasswordEncryptor.encrypt(input);

        assertThat("암호화 했는데 같다?", encryptedPassword, is(not(sameInstance(input))));
    }

    public void password_validate_test() {
        String input = "dhlee_password";
        String encryptedPassword = PasswordEncryptor.encrypt(input);

        assertTrue("패스워드가 틀렸다?", PasswordEncryptor.validatePassword(input, encryptedPassword));
    }
}