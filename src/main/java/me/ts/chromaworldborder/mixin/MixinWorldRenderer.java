package me.ts.chromaworldborder.mixin;

import me.ts.chromaworldborder.ChromaWorldBorder;
import me.ts.chromaworldborder.config.Options;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.border.WorldBorderStage;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
    @Shadow private int ticks;

    @Shadow @Nullable private ClientWorld world;

    @Redirect(
        method = "renderWorldBorder",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/border/WorldBorderStage;getColor()I"
        ))
    private int worldBorderColor(WorldBorderStage instance) {
        Options options = ChromaWorldBorder.configuration.getOptions();
        if (options.enabled) {
            float hue = (float) ((this.ticks * options.speed) % 360);
            return Color.getHSBColor(hue / 360, 1.0f, 1.0f).getRGB();
        }
        WorldBorder worldBorder = this.world.getWorldBorder();
        return worldBorder.getStage().getColor();
    }
}
