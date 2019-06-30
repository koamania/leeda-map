package ga.leeda.map.user.interfaces.service.imp;

import com.sun.crypto.provider.PBKDF2HmacSHA1Factory;
import ga.leeda.map.common.PBJDF2HashAlgorithm;
import ga.leeda.map.user.interfaces.service.exceptions.PasswordEncryptionError;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

final class PasswordEncryptor {
    public static String encrypt(String input) {
        try {
            return PBJDF2HashAlgorithm.createHash(input);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new PasswordEncryptionError();
        }
    }

    public static boolean validatePassword(String input, String storedHash) {
        try {
            return PBJDF2HashAlgorithm.validatePassword(input, storedHash);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new PasswordEncryptionError();
        }
    }
}
