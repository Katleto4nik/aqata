package com.violethitboxes.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    private static final double EXTRA_REACH = 0.07D;

    @Inject(method = "getEntityInteractionRange", at = @At("RETURN"), cancellable = true)
    private void violethitboxes$addEntityReach(CallbackInfoReturnable<Double> cir) {
        if ((Object) this == MinecraftClient.getInstance().player) {
            cir.setReturnValue(cir.getReturnValue() + EXTRA_REACH);
        }
    }
}
