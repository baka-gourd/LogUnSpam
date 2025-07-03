package cc.nptr.logunspam.forge.util;

import java.util.regex.Pattern;

public class Util {
    public static boolean shouldLog(String s) {
        for (String regex : ConfigHandler.COMMON.regexpFilterWords.get()) {
            if (regex.isEmpty()) continue;
            if (Pattern.matches(regex, s)) {
                return false;
            }
        }

        for (String word : ConfigHandler.COMMON.commonFilterWords.get()) {
            if (word.isEmpty()) continue;
            if (s.contains(word)) {
                return false;
            }
        }

        return true;
    }
}