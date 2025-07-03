package cc.nptr.logunspam.fabric.util;

import cc.nptr.logunspam.Logunspam;
import me.fzzyhmstrs.fzzy_config.config.Config;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class CommonConfig extends Config {
    public CommonConfig() {
        super(ResourceLocation.of("logunspam:config",':'));
    }

    public List<String> commonFilterWords = List.of();
    public List<String> regexpFilterWords = List.of();
    public List<String> classFilterWords = List.of();
}
