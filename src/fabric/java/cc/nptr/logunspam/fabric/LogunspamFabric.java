package cc.nptr.logunspam.fabric;

import cc.nptr.logunspam.Logunspam;
import cc.nptr.logunspam.fabric.filter.JavaFilter;
import cc.nptr.logunspam.fabric.filter.Log4jFilter;
import cc.nptr.logunspam.fabric.filter.SOutFilter;
import cc.nptr.logunspam.fabric.util.CommonConfig;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.fabricmc.api.ModInitializer;

public class LogunspamFabric implements ModInitializer {

    public static final CommonConfig COMMON = ConfigApiJava.registerAndLoadConfig(CommonConfig::new);

    @Override
    public void onInitialize() {
        Logunspam.LOGGER.info("LogUnSpam loaded.");
        JavaFilter.apply();
        SOutFilter.apply();
        Log4jFilter.apply();
        Logunspam.LOGGER.info("Filter applied.");
    }

}
