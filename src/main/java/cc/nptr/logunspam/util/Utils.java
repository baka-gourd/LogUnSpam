package cc.nptr.logunspam.util;

import java.util.List;
import java.util.regex.Pattern;

import cc.nptr.logunspam.Config;
import cc.nptr.logunspam.filter.Log4jFilter;

/**
 * The shared decision used by the {@code java.util.logging} and {@link System#out} filters.
 * <p>
 * The rules are pre-baked by {@link Config#bakeConfig()}, so this only walks two small lists.
 * The optional logger-name rule is not handled here: it is specific to {@link Log4jFilter},
 * which is the only sink that knows which class produced a record.
 */
public class Utils {
    /** @return {@code true} when the message matches none of the configured ignore rules. */
    public static boolean shouldLog(String s) {
        if (s == null) {
            return true;
        }

        List<Pattern> regexps = Config.regexps();
        for (int i = 0; i < regexps.size(); i++) {
            if (regexps.get(i).matcher(s).matches()) {
                return false;
            }
        }

        List<String> words = Config.words();
        for (int i = 0; i < words.size(); i++) {
            if (s.contains(words.get(i))) {
                return false;
            }
        }

        return true;
    }
}
