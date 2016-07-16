package eu.tjago.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by tjago on 2016-04-12.
 */
public class PasswordUtil {

    /**
     * Returns hashed password
     * @param password
     * @return
     */
    static public String hashPassword(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Verifies hashed password
     * @param candidate
     * @param hashed
     * @return true if candidate matches hashed password
     */
    static public boolean verifyHashPassword(String candidate, String hashed) {
        return BCrypt.checkpw(candidate, hashed);
    }

    @Deprecated
    static public String generatePassword() {
        return "super-pass-123";
    }
}
