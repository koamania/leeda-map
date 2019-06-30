package ga.leeda.map.user.application.service.imp;

import ga.leeda.map.common.PBJDF2HashAlgorithm;
import ga.leeda.map.user.application.service.exceptions.PasswordEncryptionError;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

final class PasswordEncryptor {
    static String encrypt(String input) {
        try {
            return PBJDF2HashAlgorithm.createHash(input);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new PasswordEncryptionError();
        }
    }

    static boolean validatePassword(String input, String storedHash) {
        try {
            return PBJDF2HashAlgorithm.validatePassword(input, storedHash);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new PasswordEncryptionError();
        }
    }
}
