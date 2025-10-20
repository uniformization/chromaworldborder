package me.ts.chromaworldborder.mixin;

import me.ts.chromaworldborder.ChromaWorldBorder;
import me.ts.chromaworldborder.config.Options;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldBorderRendering;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.border.WorldBorderStage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(WorldBorderRendering.class)
public class MixinWorldBorderRendering {
    @Unique private MinecraftClient minecraft;
    @Unique private Options options = null;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstructor(CallbackInfo ci) {
        this.minecraft = MinecraftClient.getInstance();
        this.options = ChromaWorldBorder.configuration.getOptions();
    }

    @Redirect(
        method = "updateRenderState",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/border/WorldBorderStage;getColor()I"
        ))
    private int worldBorderColor(WorldBorderStage instance, WorldBorder border) {
        if (this.options.enabled) {
            int ticks = ((IMixinWorldRenderer)this.minecraft.worldRenderer).getTicks();
            float hue = (float) ((ticks * this.options.speed) % 360);
            return Color.getHSBColor(hue / 360, 1.0f, 1.0f).getRGB();
        }
        return border.getStage().getColor();
    }
}
