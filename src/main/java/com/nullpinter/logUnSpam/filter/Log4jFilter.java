package com.nullpinter.logUnSpam.filter;

import com.nullpinter.logUnSpam.LogUnSpam;
import com.nullpinter.logUnSpam.util.ConfigHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

import java.util.regex.Pattern;

import static com.nullpinter.logUnSpam.util.Utils.shouldLog;

@Plugin(name = "Log4jFilter", category = "Core", elementType = "filter", printObject = true)
public final class Log4jFilter extends AbstractFilter {
    @Override
    public Result filter(final LogEvent event) {
        if (ConfigHandler.classFilterWords.contains(event.getLoggerFqcn())) return Result.DENY;
        Message m = event.getMessage();
        String s = m.getFormattedMessage();
        for (String regex : ConfigHandler.regexpFilterWords) {
            if (regex.equals("")) continue;
            if (Pattern.matches(regex, s)) {
                return Result.DENY;
            }
        }

        for (String word : ConfigHandler.commonFilterWords) {
            if (word.equals("")) continue;
            if (s.contains(word)) {
                return Result.DENY;
            }
        }

        return null;
    }

    public static void apply() {
        ((Logger) LogManager.getRootLogger()).addFilter(new Log4jFilter());
    }
}
