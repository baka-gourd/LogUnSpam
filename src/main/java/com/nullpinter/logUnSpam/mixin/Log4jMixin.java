package com.nullpinter.logUnSpam.mixin;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.nullpinter.logUnSpam.util.Utils.shouldLog;

@Mixin(Logger.class)
public class Log4jMixin {
    @Inject(method = "logMessage", at = @At("HEAD"),cancellable = true,remap = false)
    private static void logMessage(String fqcn, Level level, Marker marker, Message message, Throwable t, CallbackInfo ci){
        System.out.println("log_un_spam from 114514");
        if (shouldLog(message.toString()) || shouldLog(message.getFormattedMessage())) {
            ci.cancel();
        }
    }
}
