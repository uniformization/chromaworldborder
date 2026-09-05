package me.ts.chromaworldborder.mixin;

import me.ts.chromaworldborder.ChromaWorldBorder;
import me.ts.chromaworldborder.config.Options;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldBorderRenderer;
import net.minecraft.util.Util;
import net.minecraft.world.level.border.BorderStatus;
import net.minecraft.world.level.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(WorldBorderRenderer.class)
public class MixinWorldBorderRenderer {
    @Unique private Options options = null;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstructor(CallbackInfo ci) {
        this.options = ChromaWorldBorder.configuration.getOptions();
    }

    @Unique
    private float getBorderHue() {
        return (float) (((Util.getMillis() / 100.0) * this.options.speed) % 360.0);
    }

    @Redirect(
        method = "extract",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/border/BorderStatus;getColor()I"
        ))
    private int worldBorderColor(BorderStatus instance, WorldBorder border) {
        if (this.options.enabled) {
            return Color.getHSBColor(getBorderHue() / 360, 1.0f, 1.0f).getRGB();
        }
        return border.getStatus().getColor();
    }
}
