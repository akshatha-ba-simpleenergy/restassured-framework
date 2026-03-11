package utils;

import java.util.Random;

public class TestUtils {

    private static String mobile;   // shared across all tests

    // Generate ONCE and reuse
    public static String getMobile() {
        if (mobile == null) {
            String countryCode = "+91";
            int[] firstDigits = {9, 8, 7, 6};
            Random random = new Random();

            int firstDigit = firstDigits[random.nextInt(firstDigits.length)];
            int remainingDigits = 100000000 + random.nextInt(900000000);

            mobile = countryCode + firstDigit + remainingDigits;
        }
        return mobile;
    }
    
    
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final Random random = new Random();

    // Generate random alphabet string
    private static String randomAlphabet(int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        return sb.toString();
    }

    // Name: Testuser + space + alphabets
    public static String randomName() {

        String first = "Testuser";
        String second = randomAlphabet(5);

        return first + " " + second;
    }

    // Email: alphabets + numbers + domain
    public static String randomEmail() {

        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        // Random domain selection
        String domain = random.nextBoolean() ? "@gmail.com" : "@gmail.in";

        return sb.toString() + domain;
    }
    
 // Random Indian Pincode (6 digits)
    public static int randomIndianPincode() {

        int firstDigit = random.nextInt(9) + 1; // 1-9
        int remainingDigits = random.nextInt(100000); // 00000-99999

        return Integer.parseInt(firstDigit + String.format("%05d", remainingDigits));
    }
}
