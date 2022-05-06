package com.nullpinter.logUnSpam.filter;

import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static com.nullpinter.logUnSpam.util.Utils.shouldLog;

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
