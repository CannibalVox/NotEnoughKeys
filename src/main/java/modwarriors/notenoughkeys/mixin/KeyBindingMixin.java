package modwarriors.notenoughkeys.mixin;

import modwarriors.notenoughkeys.keys.KeyEvents;
import modwarriors.notenoughkeys.keys.KeyHelper;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin implements Comparable {

    @Inject(method = "setKeyBindState", at=@At("HEAD"), cancellable = true)
    private static void refreshBinds(int keyCode, boolean pressed, CallbackInfo info) {
        KeyEvents.refreshBindings();
        info.cancel();
    }

    @Inject(method="onTick", at=@At("HEAD"), cancellable = true)
    private static void incrementTicks(int keyCode, CallbackInfo info) {
        List<KeyBinding> binds = KeyHelper.getActiveKeybinds(keyCode);
        for (int i = 0; i < binds.size(); i++) {
            binds.get(i).pressTime++;
        }
        info.cancel();
    }
}
