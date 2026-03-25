package me.ts.chromaworldborder.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelRenderer.class)
public interface IMixinLevelRenderer {
    @Accessor("ticks")
    int getTicks();
}
