package com.nullpinter.logUnSpam.transformer;


import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.TransformingClassLoader;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TransformerVoteResult;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Set;

public class Log4jTransformer implements ITransformer<> {

    public static void init(TransformingClassLoader classLoader) {
        try {
            Field f = TransformingClassLoader.class.getDeclaredField("SKIP_PACKAGE_PREFIXES");
            f.setAccessible(true);
            Set<String> skipPackagePrefixes = (Set<String>) f.get(classLoader);
            skipPackagePrefixes.remove("org.apache.logging.log4j.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nonnull
    @Override
    public Object transform(Object input, ITransformerVotingContext context) {
        return null;
    }

    @Nonnull
    @Override
    public TransformerVoteResult castVote(ITransformerVotingContext context) {
        return null;
    }

    @Nonnull
    @Override
    public Set<Target> targets() {
        return null;
    }
}
