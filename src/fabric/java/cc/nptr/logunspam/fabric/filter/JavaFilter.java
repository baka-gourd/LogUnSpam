package cc.nptr.logunspam.fabric.filter;

import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static cc.nptr.logunspam.fabric.util.Util.shouldLog;

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