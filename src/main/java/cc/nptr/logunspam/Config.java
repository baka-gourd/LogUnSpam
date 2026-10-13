package cc.nptr.logunspam;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Holds the filter rules and rebuilds the runtime lookup tables whenever the config is loaded or reloaded.
 */
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    /** Validator shared by all three lists: every entry has to be a string. */
    private static final Predicate<Object> STRING_ELEMENT = o -> o instanceof String;
    /** Supplier used by the config screen to create a new, initially blank, entry. */
    private static final Supplier<String> NEW_ELEMENT = () -> "";

    public static final Common COMMON;

    public static final ModConfigSpec SPEC;

    static {
        BUILDER.comment("General Settings").push("general");
        var common = new Common(BUILDER);
        BUILDER.pop();
        COMMON = common;
        SPEC = BUILDER.build();
    }

    /**
     * The rules the running filters consult. The raw config values are kept as-is; the derived
     * tables below are the ones the hot logging paths read.
     */
    public static final class Common {
        public final ModConfigSpec.ConfigValue<List<? extends String>> commonFilterWords;
        public final ModConfigSpec.ConfigValue<List<? extends String>> regexpFilterWords;
        public final ModConfigSpec.ConfigValue<List<? extends String>> classFilterWords;

        private Common(ModConfigSpec.Builder builder) {
            this.commonFilterWords = builder.comment("Words that will be filtered out")
                    .defineListAllowEmpty("commonFilterWords", new ArrayList<>(), NEW_ELEMENT, STRING_ELEMENT);
            this.regexpFilterWords = builder.comment("Regular expressions that will be filtered out")
                    .defineListAllowEmpty("regexpFilterWords", new ArrayList<>(), NEW_ELEMENT, STRING_ELEMENT);
            this.classFilterWords = builder.comment("Fqcn that will be filtered out")
                    .defineListAllowEmpty("classFilterWords", new ArrayList<>(), NEW_ELEMENT, STRING_ELEMENT);
        }
    }

    /** Plain substrings, blank entries removed. */
    private static volatile List<String> words = List.of();
    /** {@code regexpFilterWords}, blank entries removed and pre-compiled once. */
    private static volatile List<Pattern> regexps = List.of();
    /** {@code classFilterWords}, blank entries removed. */
    private static volatile List<String> classes = List.of();

    /**
     * Re-reads the three config lists. Called from the config events and safe to call at any time.
     */
    public static void bakeConfig() {
        words = clean(COMMON.commonFilterWords.get());
        classes = clean(COMMON.classFilterWords.get());

        List<Pattern> compiled = new ArrayList<>();
        for (String regex : clean(COMMON.regexpFilterWords.get())) {
            try {
                compiled.add(Pattern.compile(regex));
            } catch (RuntimeException e) {
                // A malformed user-supplied pattern must not take the whole game down.
                LogUnSpam.LOGGER.warn("Ignoring invalid regular expression '{}' from the config", regex, e);
            }
        }
        regexps = compiled;
    }

    private static List<String> clean(List<? extends String> raw) {
        List<String> out = new ArrayList<>(raw.size());
        for (String s : raw) {
            if (s != null && !s.isEmpty()) {
                out.add(s);
            }
        }
        return out;
    }

    /** @return the non-blank substring rules, for {@code Utils#shouldLog}. */
    public static List<String> words() {
        return words;
    }

    /** @return the compiled regex rules, for {@code Utils#shouldLog}. */
    public static List<Pattern> regexps() {
        return regexps;
    }

    /** @return the non-blank logger fqcn rules, for the log4j filter. */
    public static List<String> classes() {
        return classes;
    }

    @SubscribeEvent
    public static void onModConfigLoading(final ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == SPEC) {
            bakeConfig();
        }
    }

    @SubscribeEvent
    public static void onModConfigReloading(final ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == SPEC) {
            bakeConfig();
        }
    }
}
