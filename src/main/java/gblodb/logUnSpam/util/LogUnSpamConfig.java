package gblodb.logUnSpam.util;

import gblodb.logUnSpam.LogUnSpam;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

@Config(modid = LogUnSpam.MOD_ID)
public final class LogUnSpamConfig {
    @Config.Comment("Words that will be filtered out.")
    @Config.Name("CommonFilterWords")
    @Config.RequiresWorldRestart
    public static String[] COMMON_FILTER_WORDS = new String[] {};

    @Config.Comment("Regular expressions that will be filtered out.")
    @Config.Name("RegExpFilterWords")
    @Config.RequiresWorldRestart
    public static String[] REGEXP_FILTER_WORDS = new String[] {};

    @Config.Comment("Fqcn that will be filtered out.")
    @Config.Name("ClassFilterWords")
    @Config.RequiresWorldRestart
    public static String[] CLASS_FILTER_WORDS = new String[] {};

    @Mod.EventBusSubscriber(modid = LogUnSpam.MOD_ID)
    static class ConfigSyncHandler {
        @SubscribeEvent
        public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(LogUnSpam.MOD_ID)) {
                ConfigManager.sync(LogUnSpam.MOD_ID, Config.Type.INSTANCE);
                bakeConfig();
            }
        }
    }

    public static void bakeConfig() {
        ConfigHandler.commonFilterWords = Arrays.asList(COMMON_FILTER_WORDS);
        ConfigHandler.regexpFilterWords = Arrays.asList(REGEXP_FILTER_WORDS);
        ConfigHandler.classFilterWords = Arrays.asList(CLASS_FILTER_WORDS);
        ConfigHandler.commonFilterWords.remove("");
        ConfigHandler.regexpFilterWords.remove("");
        ConfigHandler.classFilterWords.remove("");
    }
}
