package com.nullpinter.logUnSpam;

import com.nullpinter.logUnSpam.filter.JavaFilter;
import com.nullpinter.logUnSpam.filter.Log4jFilter;
import com.nullpinter.logUnSpam.filter.SOutFilter;
import com.nullpinter.logUnSpam.util.ConfigHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("log_un_spam")
public class LogUnSpam {
    public static final String MOD_ID = "log_un_spam";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public LogUnSpam() {
        LOGGER.info("LogUnSpam loaded.");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.commonSpec);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent event) {
            LOGGER.info("Applying filters...");
            LOGGER.info("Filter has {} common rule(s), {} regex rule(s).",ConfigHandler.COMMON.commonFilterWords.get().size(),ConfigHandler.COMMON.regexpFilterWords.get().size());
            JavaFilter.apply();
            SOutFilter.apply();
            Log4jFilter.apply();
            LOGGER.info("Filter applied.");
    }
}
