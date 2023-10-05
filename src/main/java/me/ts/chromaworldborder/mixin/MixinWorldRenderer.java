package me.ts.chromaworldborder.mixin;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.border.WorldBorderStage;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
    @Shadow
    @Nullable
    private ClientWorld world;

    @Shadow private int ticks;

    private double speed = 0.500;

    @Redirect(
        method = "renderWorldBorder",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/border/WorldBorderStage;getColor()I"
        ))
    private int worldBorderColor(WorldBorderStage instance) {
        float hue = (float) ((this.ticks * speed) % 360);
        return Color.getHSBColor(hue / 360, 1.0f, 1.0f).getRGB();
    }
}
