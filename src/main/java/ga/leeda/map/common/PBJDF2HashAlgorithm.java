package ga.leeda.map.common;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class PBJDF2HashAlgorithm {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    private static final int SALT_LENGTH = 24;
    private static final int HASH_LENGTH = 24;
    private static final int ITERATIONS = 1000;

    private static final int ITERATION_INDEX = 0;
    private static final int SALT_INDEX = 1;
    private static final int PBKDF2_INDEX = 2;

    public static String createHash(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return createHash(password.toCharArray());
    }

    private static String createHash(char[] password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);

        byte[] hash = pbkdf2(password, salt, ITERATIONS, HASH_LENGTH);

        return ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
    }

    public static boolean validatePassword(String password, String encryptedHash) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return validatePassword(password.toCharArray(), encryptedHash);
    }

    private static boolean validatePassword(char[] password, String encryptedHash) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String[] params = encryptedHash.split(":");
        int iteration = Integer.parseInt(params[ITERATION_INDEX]);
        byte[] salt = fromHex(params[SALT_INDEX]);
        byte[] hash = fromHex(params[PBKDF2_INDEX]);

        byte[] test = pbkdf2(password, salt, iteration, hash.length);

        return Arrays.equals(hash, test);
    }

    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }

        return binary;
    }

    private static String toHex(final byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        String hex = bi.toString(16);

        int padding = (bytes.length * 2) - hex.length();
        if (padding > 0) {
            return String.format("%0" + padding + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iteration, int bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iteration, bytes * 8);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        return factory.generateSecret(spec).getEncoded();
    }

    public static void main(String[] a) {
        try
        {
            // Print out 10 hashes
            for(int i = 0; i < 10; i++)
                System.out.println(PBJDF2HashAlgorithm.createHash("p\r\nassw0Rd!"));

            // Test password validation
            boolean failure = false;
            System.out.println("Running tests...");
            for(int i = 0; i < 100; i++)
            {
                String password = "" + i;
                String hash = createHash(password);
                String secondHash = createHash(password);
                if(hash.equals(secondHash)) {
                    System.out.println("FAILURE: TWO HASHES ARE EQUAL!");
                    failure = true;
                }
                String wrongPassword = ""+(i+1);
                if(validatePassword(wrongPassword, hash)) {
                    System.out.println("FAILURE: WRONG PASSWORD ACCEPTED!");
                    failure = true;
                }
                if(!validatePassword(password, hash)) {
                    System.out.println("FAILURE: GOOD PASSWORD NOT ACCEPTED!");
                    failure = true;
                }
            }
            if(failure)
                System.out.println("TESTS FAILED!");
            else
                System.out.println("TESTS PASSED!");
        }
        catch(Exception ex)
        {
            System.out.println("ERROR: " + ex);
        }
    }
}
