package cc.nptr.logunspam.fabric.util;

import cc.nptr.logunspam.fabric.LogunspamFabric;

import java.util.regex.Pattern;

public class Util {
    public static boolean shouldLog(String s) {
        for (String regex : LogunspamFabric.COMMON.regexpFilterWords) {
            if (regex.isEmpty()) continue;
            if (Pattern.matches(regex, s)) {
                return false;
            }
        }

        for (String word : LogunspamFabric.COMMON.commonFilterWords) {
            if (word.isEmpty()) continue;
            if (s.contains(word)) {
                return false;
            }
        }

        return true;
    }
}