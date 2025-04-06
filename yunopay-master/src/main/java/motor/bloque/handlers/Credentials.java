package motor.bloque.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class Credentials {

    private static final Logger logger = LogManager.getLogger(Credentials.class);

    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static Random random = new Random();
    private static String salt;
    private static String hashedPin;

    public static Map<HASHED, String> hashNewPassword(String passwordToHash) {
        byte[] salt = getSalt();
        if(salt == null) return null;
        String securePassword = getSecurePassword(passwordToHash, salt);
        Map<HASHED, String> map = new EnumMap(HASHED.class);
        map.put(HASHED.PASSWORD, securePassword);
        map.put(HASHED.SALT, bytesToHex(salt));
        return map;
    }

    static boolean validatePassword(String password, String cardNumber) {
        Persistence.requestCredentials(cardNumber);
        String toValidate = getSecurePassword(password, hexStringToByteArray(salt));
        boolean validated = false;
        if (toValidate != null) validated = toValidate.equals(hashedPin);
        salt = "";
        hashedPin = "";
        return validated;
    }

    public static String generateCardNumber() {
        int length = 12;
        char[] digits = new char[length];
        for (int i = 0; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        String cardNumber = new String(digits);
        if(Persistence.existsCard(cardNumber)) return generateCardNumber();
        return cardNumber;
    }

    static void setSalt(String salt) {
        Credentials.salt = salt;
    }

    public static void setPin(String hashedPin) {
        Credentials.hashedPin = hashedPin;
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private static String getSecurePassword(String passwordToHash, byte[] salt) {
        byte[] generatedPassword;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            generatedPassword = md.digest(passwordToHash.getBytes());
        } catch (NoSuchAlgorithmException e) {
            logger.error(e);
            return null;
        }
        return bytesToHex(generatedPassword);
    }

    private static byte[] getSalt(){
        byte[] salt = null;
        try{
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            salt = new byte[16];
            sr.nextBytes(salt);

        } catch (NoSuchAlgorithmException nsa){
            logger.error(nsa);
        }
        return salt;
    }

    public enum HASHED {PASSWORD, SALT}

}
