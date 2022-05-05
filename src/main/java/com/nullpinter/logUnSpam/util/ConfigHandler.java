package com.nullpinter.logUnSpam.util;

import com.nullpinter.logUnSpam.LogUnSpam;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = LogUnSpam.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigHandler {
    public static ForgeConfigSpec CONFIG;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> COMMON_FILTER_WORDS;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> REGEXP_FILTER_WORDS;

    public static final List<String> DefaultList = Collections.singletonList("");

    public static List<? extends String> commonFilterWords;
    public static List<? extends String> regexpFilterWords;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("General Settings").push("general");
        COMMON_FILTER_WORDS = builder.comment("Words that will be filtered out")
                .worldRestart()
                .defineList("commonFilterWords",DefaultList,o -> o instanceof String);
        REGEXP_FILTER_WORDS = builder.comment("Regular expressions that will be filtered out")
                .worldRestart()
                .defineList("regexpFilterWords",DefaultList,o -> o instanceof String);
        builder.pop();
        CONFIG = builder.build();
    }

    public static void bakeConfig(){
        commonFilterWords = COMMON_FILTER_WORDS.get();
        regexpFilterWords = REGEXP_FILTER_WORDS.get();
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent) {
        if (configEvent.getConfig().getSpec() == CONFIG) {
            bakeConfig();
        }
    }
}
