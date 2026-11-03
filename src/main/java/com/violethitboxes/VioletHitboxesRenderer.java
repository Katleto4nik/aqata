package com.violethitboxes;

import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexRendering;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public final class VioletHitboxesRenderer {
    private static final int PURPLE = 0xFFB34DFF;
    private static final float LINE_WIDTH = 2.0F;
    public static final int WEB_SCAN_RADIUS = 16;

    private VioletHitboxesRenderer() {}

    public static void render(WorldRenderContext context) {
        if (!VioletHitboxesClient.enabled) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null) return;

        Vec3d camera = client.gameRenderer.getCamera().getPos();
        var matrices = context.matrices();
        VertexConsumer lines = context.consumers().getBuffer(RenderLayer.getLines());

        // Entity hitboxes: only the purple body box is drawn. The vanilla eye/look vector
        // and head marker are intentionally omitted.
        for (Entity entity : client.world.getEntities()) {
            if (entity == client.player && client.options.getPerspective().isFirstPerson()) continue;
            Box box = entity.getBoundingBox();
            VoxelShape shape = VoxelShapes.cuboid(box);
            VertexRendering.drawOutline(matrices, lines, shape,
                    -camera.x, -camera.y, -camera.z, PURPLE, LINE_WIDTH);
        }

        drawCobwebs(matrices, lines, camera);
    }

    private static void drawCobwebs(net.minecraft.client.util.math.MatrixStack matrices,
                                    VertexConsumer lines, Vec3d camera) {
        for (BlockPos pos : VioletHitboxesClient.cachedCobwebs) {
            MinecraftClient client = MinecraftClient.getInstance();
            VoxelShape shape = client.world.getBlockState(pos).getOutlineShape(client.world, pos);
            VertexRendering.drawOutline(matrices, lines, shape,
                    pos.getX() - camera.x, pos.getY() - camera.y, pos.getZ() - camera.z,
                    PURPLE, LINE_WIDTH);
        }
    }

}
