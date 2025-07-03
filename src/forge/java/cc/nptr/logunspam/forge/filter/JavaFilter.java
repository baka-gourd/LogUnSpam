package cc.nptr.logunspam.forge.filter;

import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static cc.nptr.logunspam.forge.util.Util.shouldLog;

public class JavaFilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        String s = record.getMessage();
        return shouldLog(s);
    }

    public static void apply(){
        Logger.getLogger("").setFilter(new JavaFilter());
    }
}