package com.nullpinter.logUnSpam;

import com.mojang.bridge.launcher.LauncherProvider;
import com.nullpinter.logUnSpam.filter.JavaFilter;
import com.nullpinter.logUnSpam.filter.Log4jFilter;
import com.nullpinter.logUnSpam.filter.SOutFilter;
import com.nullpinter.logUnSpam.util.ConfigHandler;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.TransformingClassLoader;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("log_un_spam")
public class LogUnSpam
{
    public static final String MOD_ID = "log_un_spam";
    private static final Logger LOGGER = LogManager.getLogger();

    public LogUnSpam() throws NoSuchFieldException {
        System.out.println("LogUnSpam loaded.");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,ConfigHandler.CONFIG);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent event) {
        LOGGER.info("Applying filters...");
        JavaFilter.applyFilter();
        SOutFilter.applyFilter();
        //Log4jFilter.applyFilter();
    }

}
