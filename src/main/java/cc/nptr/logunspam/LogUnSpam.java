package cc.nptr.logunspam;

import cc.nptr.logunspam.filter.JavaFilter;
import cc.nptr.logunspam.filter.Log4jFilter;
import cc.nptr.logunspam.filter.SOutFilter;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

/**
 * Filters unwanted lines out of the game's logs.
 * <p>
 * Three independent sinks have to be covered, because mods and the game itself write to all of them:
 * {@code java.util.logging}, log4j2 (the one Minecraft and NeoForge actually use) and plain
 * {@link System#out}.
 */
// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(LogUnSpam.MOD_ID)
public class LogUnSpam {
    /** The mod id; must match {@code mod_id} in gradle.properties and the entries in neoforge.mods.toml. */
    public static final String MOD_ID = "logunspam";
    /** Directly reference an slf4j logger. */
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public LogUnSpam(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the config event handlers so the filtered word lists are re-read on reload
        modEventBus.register(Config.class);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us.
        // The COMMON config is read before FMLCommonSetupEvent is fired, so the filters below
        // already see the user's rules.
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // The COMMON config is read before this event, so this works. Baking again here does not
        // rely on the config event having been delivered to us, which keeps this a single code path.
        Config.bakeConfig();
        LOGGER.info("Applying filters...");
        LOGGER.info("Filter has {} common rule(s), {} regex rule(s), {} class rule(s).",
                Config.COMMON.commonFilterWords.get().size(),
                Config.COMMON.regexpFilterWords.get().size(),
                Config.COMMON.classFilterWords.get().size());
        JavaFilter.apply();
        SOutFilter.apply();
        Log4jFilter.apply();
        LOGGER.info("Filter applied.");
    }
}
