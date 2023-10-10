package me.ts.chromaworldborder;

import me.ts.chromaworldborder.config.Configuration;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChromaWorldBorder implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("chromaworldborder");

	public static final Configuration configuration = new Configuration();

	@Override
	public void onInitialize() {}
}
