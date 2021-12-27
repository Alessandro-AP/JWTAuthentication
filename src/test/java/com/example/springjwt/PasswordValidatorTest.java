package com.example.springjwt;

import com.example.springjwt.utils.PasswordValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is used to test the correct functionality of the PasswordValidator class.
 */
public class PasswordValidatorTest {

    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @MethodSource("validPasswordProvider")
    void test_password_regex_valid(String password) {
        assertTrue(PasswordValidator.isValidPassword(password));
    }

    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @MethodSource("invalidPasswordProvider")
    void test_password_regex_invalid(String password) {
        assertFalse(PasswordValidator.isValidPassword(password));
    }

    static Stream<String> validPasswordProvider() {
        return Stream.of(
                "AAAbbbccc@123",
                "Hello world$123",
                "Xkxtxyglu@z9Afacb-k1",
                "Amtech21.",
                "A!@#&()–a1",               // test punctuation part 1
                "A[{}]:;',?/*a1",           // test punctuation part 2
                "A~$^+=<>a1",               // test symbols
                "0123456789$abcdefgAB",     // test 20 chars
                "123Aa$Aa"                  // test 8 chars
        );
    }

    // At least
    // one lowercase character,
    // one uppercase character,
    // one digit,
    // one special character
    // and length more than 8 characters.
    static Stream<String> invalidPasswordProvider() {
        return Stream.of(
                "12345678",                 // invalid, only digit
                "abcdefgh",                 // invalid, only lowercase
                "ABCDEFGH",                 // invalid, only uppercase
                "abc123$$$",                // invalid, at least one uppercase
                "ABC123$$$",                // invalid, at least one lowercase
                "ABC$$$$$$",                // invalid, at least one digit
                "java REGEX 123",           // invalid, at least one special character
                "java REGEX 123 %",         // invalid, % is not in the special character group []
                "Banan9.",                  // invalid, less than 8 characters
                "________",                 // invalid
                "--------",                 // invalid
                " ",                        // empty
                "");                        // empty
    }
}
