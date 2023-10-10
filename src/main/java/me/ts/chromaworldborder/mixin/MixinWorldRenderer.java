package me.ts.chromaworldborder.mixin;

import me.ts.chromaworldborder.ChromaWorldBorder;
import me.ts.chromaworldborder.config.Options;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.border.WorldBorderStage;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
    @Unique private Options options = null;

    @Shadow private int ticks;

    @Shadow @Nullable private ClientWorld world;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstructor(MinecraftClient client, EntityRenderDispatcher entityRenderDispatcher, BlockEntityRenderDispatcher blockEntityRenderDispatcher, BufferBuilderStorage bufferBuilders, CallbackInfo ci) {
        this.options = ChromaWorldBorder.configuration.getOptions();
    }

    @Redirect(
        method = "renderWorldBorder",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/border/WorldBorderStage;getColor()I"
        ))
    private int worldBorderColor(WorldBorderStage instance) {
        if (this.options.enabled) {
            float hue = (float) ((this.ticks * this.options.speed) % 360);
            return Color.getHSBColor(hue / 360, 1.0f, 1.0f).getRGB();
        }
        WorldBorder worldBorder = this.world.getWorldBorder();
        return worldBorder.getStage().getColor();
    }
}
