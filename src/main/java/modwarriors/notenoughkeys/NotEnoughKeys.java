package modwarriors.notenoughkeys;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modwarriors.notenoughkeys.api.Api;
import modwarriors.notenoughkeys.coremod.NotEnoughKeysResourcePack;
import modwarriors.notenoughkeys.keys.KeyEvents;
import modwarriors.notenoughkeys.keys.KeyHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.io.File;

/**
 * Main NEK class
 *
 * @author TheTemportalist
 */
public class NotEnoughKeys extends DummyModContainer {

	public NotEnoughKeys() {
		super(new ModMetadata());
		ModMetadata metadata = getMetadata();
		metadata.modId = NotEnoughKeys.modid;
		metadata.version = NotEnoughKeys.version;
		metadata.name = NotEnoughKeys.name;
		metadata.description = "Upddated Version of Okushama's NotEnoughKeys. Sorts the Controls gui system and adds optional modifiers (SHIFT, CTRL, ALT) to keys.";
		metadata.url = "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2277165-notenoughkeys";
		metadata.authorList.add("dmodoomsirius");
		metadata.authorList.add("okushama");
		metadata.authorList.add("Parker8283");
		metadata.authorList.add("TheTemportalist");
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}

	@Override
	public Class<?> getCustomResourcePackClass() { return NotEnoughKeysResourcePack.class; }

	public static final String modid = "notenoughkeys", name = "Not Enough Keys", version = "@MOD_VERSION@";

	public static Logger logger;

	private static Configuration config;

	@Subscribe
	@SideOnly(Side.CLIENT)
	public static void preInit(FMLPreInitializationEvent e) {
		logger = e.getModLog();

		Object eventhandler = new KeyEvents();
		FMLCommonHandler.instance().bus().register(eventhandler);
		MinecraftForge.EVENT_BUS.register(eventhandler);
		//KeyHelper.registerMod(NotEnoughKeys.name, new String[] {});

		NotEnoughKeys.configure(e.getModConfigurationDirectory());

	}

	private static void configure(File configDir) {
		NotEnoughKeys.config = new Configuration(new File(configDir, "NotEnoughKeys.cfg"));
		NotEnoughKeys.config.load();
		NotEnoughKeys.loadConfig();
		NotEnoughKeys.saveConfig();
	}

	@Subscribe
	@SideOnly(Side.CLIENT)
	public static void postInit(FMLPostInitializationEvent e) {
		/*
		for (ModContainer mod : Loader.instance().getActiveModList())
			KeybindTracker.modids.add(mod.getModId());
		*/
		KeyHelper.pullKeyBindings();

		NotEnoughKeys.loadConfig();
		NotEnoughKeys.saveConfig();
	}

	private static void loadConfig() {
		KeyHelper.alternates.clear();
		for (String modid : KeyHelper.compatibleMods.keySet()) {
			for (String keyDesc : KeyHelper.compatibleMods.get(modid)) {
				KeyHelper.alternates.put(
						keyDesc,
						NotEnoughKeys.config.get(
								"Key Modifiers", keyDesc,
								new boolean[] { false, false, false }
						).getBooleanList()
				);
			}
		}
	}

	public static void saveConfig() {
		// Iterate through the categories of keybindings
		for (String keyDesc : KeyHelper.alternates.keySet()) {
			NotEnoughKeys.config.get(
					"Key Modifiers", keyDesc,
					new boolean[] { false, false, false }
			).set(KeyHelper.alternates.get(keyDesc));
		}
		NotEnoughKeys.config.save();
	}

}
