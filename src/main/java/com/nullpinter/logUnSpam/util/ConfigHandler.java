package com.nullpinter.logUnSpam.util;

import com.nullpinter.logUnSpam.LogUnSpam;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = LogUnSpam.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigHandler {
    public static class CommonConfig {
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> commonFilterWords;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> regexpFilterWords;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> classFilterWords;

        CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("General Settings").push("general");
            this.commonFilterWords = builder.comment("Words that will be filtered out")
                    .worldRestart()
                    .defineList("commonFilterWords", new ArrayList<>(), o -> o instanceof String);
            this.regexpFilterWords = builder.comment("Regular expressions that will be filtered out")
                    .worldRestart()
                    .defineList("regexpFilterWords", new ArrayList<>(), o -> o instanceof String);
            this.classFilterWords = builder.comment("Fqcn that will be filtered out")
                    .worldRestart()
                    .defineList("classFilterWords", new ArrayList<>(), o -> o instanceof String);
            builder.pop();
        }
    }

    public static final ForgeConfigSpec commonSpec;
    public static final ConfigHandler.CommonConfig COMMON;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        commonSpec = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }

    public static void bakeConfig(){
        COMMON.commonFilterWords.get().remove("");
        COMMON.regexpFilterWords.get().remove("");
        COMMON.classFilterWords.get().remove("");
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent) {
        if (configEvent.getConfig().getSpec() == commonSpec) {
            bakeConfig();
        }
    }
}
