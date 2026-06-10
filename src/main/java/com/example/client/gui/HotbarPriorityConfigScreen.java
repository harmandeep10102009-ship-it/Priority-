package com.example.client.gui;

import com.example.config.HotbarPriorityConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;

public class HotbarPriorityConfigScreen {

    public static Screen create(Screen parent) {
        HotbarPriorityConfig config = AutoConfig.getConfigHolder(HotbarPriorityConfig.class).getConfig();

        ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(new TranslatableText("text.autoconfig.hotbarpriority.title"));

        ConfigCategory category = builder.getOrCreateCategory(
            new TranslatableText("text.autoconfig.hotbarpriority.category.default")
        );

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        String[] slotKeys = {
            "slot0Priority", "slot1Priority", "slot2Priority",
            "slot3Priority", "slot4Priority", "slot5Priority",
            "slot6Priority", "slot7Priority", "slot8Priority"
        };

        for (int i = 0; i < 9; i++) {
            final int slotIndex = i;
            category.addEntry(
                entryBuilder.startIntField(
                    new TranslatableText("text.autoconfig.hotbarpriority.option." + slotKeys[i]),
                    config.getSlotPriority(i)
                )
                .setDefaultValue(HotbarPriorityConfig.getDefaultPriority(i))
                .setMin(1)
                .setMax(9)
                .setSaveConsumer(value -> config.setSlotPriority(slotIndex, value))
                .build()
            );
        }

        builder.setSavingRunnable(() ->
            AutoConfig.getConfigHolder(HotbarPriorityConfig.class).save()
        );

        return builder.build();
    }
}
