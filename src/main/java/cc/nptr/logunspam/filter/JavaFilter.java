package cc.nptr.logunspam.filter;

import static cc.nptr.logunspam.util.Utils.shouldLog;

import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Covers the {@code java.util.logging} sink.
 */
public class JavaFilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        String s = record.getMessage();
        return shouldLog(s);
    }

    public static void apply() {
        Logger.getLogger("").setFilter(new JavaFilter());
    }
}
