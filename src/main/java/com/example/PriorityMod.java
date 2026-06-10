package com.example;

import com.example.config.HotbarPriorityConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PriorityMod implements ModInitializer {

    public static final String MOD_ID = "priority";
    public static final Logger LOGGER = LogManager.getLogger("Priority");

    @Override
    public void onInitialize() {
        AutoConfig.register(HotbarPriorityConfig.class, GsonConfigSerializer::new);
        LOGGER.info("Hotbar Priority mod initialized!");
    }
}
