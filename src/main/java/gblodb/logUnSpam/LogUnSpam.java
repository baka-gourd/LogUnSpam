package gblodb.logUnSpam;

import com.nullpinter.logUnSpam.filter.*;
import gblodb.logUnSpam.util.ConfigHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = LogUnSpam.MOD_ID, name = LogUnSpam.MOD_NAME, version = LogUnSpam.VERSION)
public class LogUnSpam {
    public static final String MOD_ID = "log_un_spam";
    public static final String MOD_NAME = "LogUnSpam";
    public static final String VERSION = "1.0-1.12";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Mod.Instance(MOD_ID)
    public static LogUnSpam INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        LOGGER.info("LogUnSpam loaded.");
        LOGGER.info("Applying filters...");
        LOGGER.info("Filter has {} common rule(s), {} regex rule(s) and {} Fqcn rule(s).", ConfigHandler.commonFilterWords.size(), ConfigHandler.regexpFilterWords.size(), ConfigHandler.classFilterWords.size());
        JavaFilter.apply();
        SOutFilter.apply();
        Log4jFilter.apply();
        LOGGER.info("Filters applied.");
    }

}
