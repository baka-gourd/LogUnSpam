package cc.nptr.logunspam.forge;

import cc.nptr.logunspam.Logunspam;
import cc.nptr.logunspam.forge.filter.JavaFilter;
import cc.nptr.logunspam.forge.filter.Log4jFilter;
import cc.nptr.logunspam.forge.filter.SOutFilter;
import cc.nptr.logunspam.forge.util.ConfigHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Logunspam.MOD_ID)
public class LogunspamForge {

    public LogunspamForge() throws NoSuchFieldException {
        Logunspam.LOGGER.info("LogUnSpam loaded.");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.commonSpec);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onInit);
    }

    public void onInit(FMLCommonSetupEvent event) {
        Logunspam.LOGGER.info("Applying filters...");
        Logunspam.LOGGER.info("Filter has {} common rule(s), {} regex rule(s).",ConfigHandler.COMMON.commonFilterWords.get().size(),ConfigHandler.COMMON.regexpFilterWords.get().size());
        JavaFilter.apply();
        SOutFilter.apply();
        Log4jFilter.apply();
        Logunspam.LOGGER.info("Filter applied.");
    }
}
