package com.example.mixin.client;

import com.example.config.HotbarPriorityConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    private int winningKeyIndex = -1;
    private int highestPriority = Integer.MAX_VALUE;
    private boolean checkedThisTick = false;

    @Redirect(
        method = "handleInputEvents",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/options/KeyBinding;wasPressed()Z"
        )
    )
    private boolean redirectWasPressed(KeyBinding keyBinding) {
        MinecraftClient client = (MinecraftClient) (Object) this;

        KeyBinding[] hotbarKeys = client.options.keysHotbar;
        int thisKeyIndex = -1;
        for (int i = 0; i < hotbarKeys.length; i++) {
            if (hotbarKeys[i] == keyBinding) {
                thisKeyIndex = i;
                break;
            }
        }

        if (thisKeyIndex == -1) {
            return keyBinding.wasPressed();
        }

        HotbarPriorityConfig config = AutoConfig.getConfigHolder(HotbarPriorityConfig.class).getConfig();

        if (!checkedThisTick) {
            winningKeyIndex = -1;
            highestPriority = Integer.MAX_VALUE;

            for (int i = 0; i < hotbarKeys.length; i++) {
                if (hotbarKeys[i].isPressed()) {
                    int priority = config.getSlotPriority(i);
                    if (priority < highestPriority) {
                        highestPriority = priority;
                        winningKeyIndex = i;
                    }
                }
            }
            checkedThisTick = true;
        }

        if (thisKeyIndex == winningKeyIndex) {
            checkedThisTick = false;
            return keyBinding.wasPressed();
        } else {
            while (keyBinding.wasPressed()) { }
            return false;
        }
    }
}
