package cc.nptr.logunspam.filter;

import static cc.nptr.logunspam.util.Utils.shouldLog;

import cc.nptr.logunspam.Config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.filter.AbstractFilter;

/**
 * Covers the log4j2 sink, which is what Minecraft, NeoForge and most mods log through.
 * <p>
 * This is the only filter that can drop a line by logger name, because the originating class is
 * not part of a {@link java.util.logging.LogRecord} or of a line written to {@link System#out}.
 */
@Plugin(name = "Log4jFilter", category = "Core", elementType = "filter", printObject = true)
public final class Log4jFilter extends AbstractFilter {
    @Override
    public Result filter(final LogEvent event) {
        if (Config.classes().contains(event.getLoggerFqcn())) {
            return Result.DENY;
        }
        return shouldLog(event.getMessage().getFormattedMessage()) ? Result.NEUTRAL : Result.DENY;
    }

    public static void apply() {
        ((Logger) LogManager.getRootLogger()).addFilter(new Log4jFilter());
    }
}
