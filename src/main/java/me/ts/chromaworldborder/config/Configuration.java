package me.ts.chromaworldborder.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.ts.chromaworldborder.ChromaWorldBorder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class Configuration {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().setLenient().create();
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("chromaworldborder.json");

    private Options options;

    public Configuration() {
        this.options = loadConfig();
    }

    public Options getOptions() {
        return options;
    }

    public Options loadConfig() {
        if (!Files.exists(CONFIG_FILE)) {
            this.options = new Options();
            saveConfig();
        }
        try (BufferedReader reader = Files.newBufferedReader(CONFIG_FILE)) {
            return GSON.fromJson(reader, Options.class);
        } catch (Exception e) {
            ChromaWorldBorder.LOGGER.error("Failed to read configuration!", e);
        }
        return new Options();
    }

    public void saveConfig() {
        try {
            if (CONFIG_FILE.toFile().createNewFile()) {
                ChromaWorldBorder.LOGGER.info("Created configuration file!");
            }
            try (BufferedWriter writer = Files.newBufferedWriter(CONFIG_FILE)) {
                GSON.toJson(options, writer);
                ChromaWorldBorder.LOGGER.info("Saved configuration file!");
            }
        } catch (Exception e) {
            ChromaWorldBorder.LOGGER.error("Failed to save configuration!", e);
        }
    }
}
