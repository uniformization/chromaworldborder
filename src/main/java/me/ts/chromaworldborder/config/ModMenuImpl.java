package me.ts.chromaworldborder.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.ts.chromaworldborder.ChromaWorldBorder;
import net.minecraft.text.Text;

public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("config.chromaworldborder.title"));
            ConfigCategory mainCategory = builder.getOrCreateCategory(Text.translatable("config.chromaworldborder.category.main"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            Options options = ChromaWorldBorder.configuration.getOptions();

            mainCategory.addEntry(entryBuilder.startBooleanToggle(Text.translatable("config.chromaworldborder.options.enabled"), options.enabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> options.enabled = newValue)
                .build());

            mainCategory.addEntry(entryBuilder.startDoubleField(Text.translatable("config.chromaworldborder.options.speed"), options.speed)
                .setDefaultValue(0.5)
                .setMin(0.1)
                .setMax(10.0)
                .setTooltip(Text.translatable("config.chromaworldborder.options.speed.tooltip"))
                .setSaveConsumer(newValue -> options.speed = newValue)
                .build());

            builder.setSavingRunnable(ChromaWorldBorder.configuration::saveConfig);
            return builder.build();
        };
    }
}
