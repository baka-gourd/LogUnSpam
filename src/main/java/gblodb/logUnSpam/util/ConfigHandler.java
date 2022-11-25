package gblodb.logUnSpam.util;

import java.util.List;

public final class ConfigHandler {
    public static List<String> commonFilterWords;
    public static List<String> regexpFilterWords;
    public static List<String> classFilterWords;

    static {
        LogUnSpamConfig.bakeConfig();
    }
}