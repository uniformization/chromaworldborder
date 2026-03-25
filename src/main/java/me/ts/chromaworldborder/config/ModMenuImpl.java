package me.ts.chromaworldborder.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.DoubleSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.gui.controllers.slider.DoubleSliderController;
import me.ts.chromaworldborder.ChromaWorldBorder;
import net.minecraft.network.chat.Component;

public class ModMenuImpl implements ModMenuApi {
    private final Options defaultOptions = new Options();

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            Options options = ChromaWorldBorder.configuration.getOptions();

            return YetAnotherConfigLib.createBuilder()
                    .title(Component.translatable("config.chromaworldborder.title"))
                    .category(ConfigCategory.createBuilder()
                            .name(Component.translatable("config.chromaworldborder.category.main"))
                            .option(Option.<Boolean>createBuilder()
                                    .name(Component.translatable("config.chromaworldborder.options.enabled"))
                                    .binding(defaultOptions.enabled, () -> options.enabled, newValue -> options.enabled = newValue)
                                    .controller(TickBoxControllerBuilder::create)
                                    .build())
                            .option(Option.<Double>createBuilder()
                                    .name(Component.translatable("config.chromaworldborder.options.speed"))
                                    .description(OptionDescription.of(Component.translatable("config.chromaworldborder.options.speed.tooltip")))
                                    .binding(defaultOptions.speed, () -> options.speed, newValue -> options.speed = newValue)
                                    .controller(opt -> DoubleSliderControllerBuilder
                                            .create(opt)
                                            .range(0.1, 10.0)
                                            .step(0.1))
                                    .build())
                            .build())
                    .save(ChromaWorldBorder.configuration::saveConfig)
                    .build()
                    .generateScreen(parent);
        };
    }
}
