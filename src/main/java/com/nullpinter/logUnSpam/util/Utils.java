package com.nullpinter.logUnSpam.util;

import java.util.regex.Pattern;

public class Utils {
    public static boolean shouldLog(String s) {
        for (String regex : ConfigHandler.regexpFilterWords) {
            if (regex.equals("")) continue;
            if (Pattern.matches(regex, s)) {
                return false;
            }
        }

        for (String word : ConfigHandler.commonFilterWords) {
            if (word.equals("")) continue;
            if (s.contains(word)) {
                return false;
            }
        }

        return true;
    }
}
