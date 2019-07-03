package ga.leeda.map.user.application.service.imp;

import ga.leeda.map.common.PBKDF2HashAlgorithm;
import ga.leeda.map.user.application.service.exceptions.PasswordEncryptionError;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 비밀번호 암호화 및 유효성 여부를 검사하는 util 클래스
 */
final class PasswordEncryptor {
    private PasswordEncryptor() {
        // no - op
    }

    /**
     * 입력한 비밀번호를 암호화한다.
     *
     * @param input
     * @return
     */
    static String encrypt(String input) {
        try {
            return PBKDF2HashAlgorithm.createHash(input);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new PasswordEncryptionError();
        }
    }

    /**
     * 입력한 비밀번호와 hash를 비교해 올바른 비밀번호인지 확인한다.
     *
     * @param input
     * @param storedHash
     * @return
     */
    static boolean validatePassword(String input, String storedHash) {
        try {
            return PBKDF2HashAlgorithm.validatePassword(input, storedHash);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new PasswordEncryptionError();
        }
    }
}
