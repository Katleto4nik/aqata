package com.violethitboxes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Blocks;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.glfw.GLFW;

public final class VioletHitboxesClient implements ClientModInitializer {
    public static boolean enabled = false;
    private static boolean previousB = false;
    private static int cacheTicks = 0;
    public static List<BlockPos> cachedCobwebs = List.of();

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(VioletHitboxesClient::handleTick);
        WorldRenderEvents.BEFORE_DEBUG_RENDER.register(VioletHitboxesRenderer::render);
    }

    private static void handleTick(MinecraftClient client) {
        handleF3B(client);
        refreshCobwebCache(client);
    }

    private static void refreshCobwebCache(MinecraftClient client) {
        if (!enabled || client.world == null || client.player == null) {
            cachedCobwebs = List.of();
            return;
        }
        if (++cacheTicks < 10) return;
        cacheTicks = 0;
        BlockPos center = client.player.getBlockPos();
        List<BlockPos> found = new ArrayList<>();
        int radius = VioletHitboxesRenderer.WEB_SCAN_RADIUS;
        BlockPos.Mutable pos = new BlockPos.Mutable();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    pos.set(center.getX() + x, center.getY() + y, center.getZ() + z);
                    if (client.world.getBlockState(pos).isOf(Blocks.COBWEB)) found.add(pos.toImmutable());
                }
            }
        }
        cachedCobwebs = List.copyOf(found);
    }

    private static void handleF3B(MinecraftClient client) {
        if (client.getWindow() == null || client.currentScreen != null) return;

        long window = client.getWindow().getHandle();
        boolean f3Down = InputUtil.isKeyPressed(window, GLFW.GLFW_KEY_F3);
        boolean bDown = InputUtil.isKeyPressed(window, GLFW.GLFW_KEY_B);

        if (f3Down && bDown && !previousB) {
            enabled = !enabled;
        }
        previousB = bDown;
    }
}
