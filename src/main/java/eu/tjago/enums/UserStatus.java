package eu.tjago.enums;

/**
 * Created by tjago on 2016-04-12.
 */
public enum UserStatus {

    /** Human User */
    HAM(0),

    /** spam user */
    SPAM(1);

    private final int value;

    UserStatus(int val) {
        value = val;
    }

    public int getValue() {
        return value;
    }

}
