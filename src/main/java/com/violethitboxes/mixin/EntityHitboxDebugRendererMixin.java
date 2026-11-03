package com.violethitboxes.mixin;

import com.violethitboxes.VioletHitboxesClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.debug.EntityHitboxDebugRenderer;
import net.minecraft.world.debug.DebugDataStore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityHitboxDebugRenderer.class)
public abstract class EntityHitboxDebugRendererMixin {
    // Prevent vanilla's white boxes, red head marker and blue look vector from being drawn.
    // The mod draws its replacement in BEFORE_DEBUG_RENDER while F3+B is enabled.
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void violethitboxes$replaceVanilla(double cameraX, double cameraY, double cameraZ,
                                               DebugDataStore store, Frustum frustum,
                                               float tickProgress, CallbackInfo ci) {
        ci.cancel();
    }
}
