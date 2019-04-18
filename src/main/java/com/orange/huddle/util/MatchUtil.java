package com.orange.huddle.util;

import java.util.Collection;

public class MatchUtil {
    public static String getUsersConcat(String user1, String user2) {

        if (user1.compareTo(user2) > 0) {
            String temp = user1;
            user1 = user2;
            user2 = temp;
        }

        return user1 + "+" + user2;
    }

    public static String getUsersConcat(Collection<String> users) {
        try {
            return getUsersConcat((String) users.toArray()[0], (String) users.toArray()[1]);

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
    }
}
