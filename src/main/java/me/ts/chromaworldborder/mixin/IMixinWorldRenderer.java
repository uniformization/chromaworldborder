package me.ts.chromaworldborder.mixin;

import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldRenderer.class)
public interface IMixinWorldRenderer {
    @Accessor("ticks")
    int getTicks();
}
