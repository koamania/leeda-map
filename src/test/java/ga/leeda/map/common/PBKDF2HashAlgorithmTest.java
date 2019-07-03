package ga.leeda.map.common;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static ga.leeda.map.common.PBKDF2HashAlgorithm.createHash;
import static ga.leeda.map.common.PBKDF2HashAlgorithm.validatePassword;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class PBKDF2HashAlgorithmTest {

    @Test
    public void PBKDF2_hash_create_test() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String samePassword1 = createHash("leeda_password");
        String samePassword2 = createHash("leeda_password");

        // 충돌 확률이 거의 없지만 만약 발생하면 로또에 쓸 운을 다 쓴거다
        assertNotSame("같은 password input인데 동일한 hash 생성", samePassword1, samePassword2);

        String diffPassword1 = createHash("diff_password");

        // 당연히 달라야 한다
        assertNotSame("서로 다른 password input인데 동일한 hash 생성", samePassword1, diffPassword1);
    }

    @Test
    public void PBKDF_hash_validate_test() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String inputPassword = "pa$s0rd";
        String hashString = createHash(inputPassword);

        assertTrue("같은 input password인데 validate 하지 않음", validatePassword(inputPassword, hashString));
    }
}