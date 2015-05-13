package modwarriors.notenoughkeys.keys;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modwarriors.notenoughkeys.Helper;
import modwarriors.notenoughkeys.NotEnoughKeys;
import modwarriors.notenoughkeys.api.Api;
import modwarriors.notenoughkeys.api.KeyBindingPressedEvent;
import modwarriors.notenoughkeys.gui.GuiControlsOverride;
import modwarriors.notenoughkeys.gui.GuiKeybindsMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class KeyEvents {
	private Minecraft mc = Minecraft.getMinecraft();

	/**
	 * Takes care of overriding the controls screen
	 *
	 * @param event
	 */
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event) {
		if (event.gui != null && event.gui.getClass().equals(GuiControls.class)
				&& !(event.gui instanceof GuiControlsOverride)) {
			event.gui = new GuiKeybindsMenu();
		}
	}

	public static void refreshBindings() {
        for (Integer key : KeyHelper.bindingsByKey.keySet()) {
            List<KeyBinding> activeBindings = KeyHelper.getActiveKeybinds(key);

            for (KeyBinding binding : KeyHelper.bindingsByKey.get(key)) {
                boolean nowPressed = activeBindings.contains(binding);
                boolean wasPressed = binding.getIsKeyPressed();

                if (wasPressed != nowPressed) {
                    binding.pressed = nowPressed;
                    if (Minecraft.getMinecraft().currentScreen == null && KeyHelper.alternates.containsKey(binding.getKeyDescription())) {
                        MinecraftForge.EVENT_BUS.post(new KeyBindingPressedEvent(binding, KeyHelper.alternates.get(binding.getKeyDescription())));
                    }
                }
            }
        }
    }
}
