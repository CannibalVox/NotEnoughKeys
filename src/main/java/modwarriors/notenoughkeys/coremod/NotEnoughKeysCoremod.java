package modwarriors.notenoughkeys.coremod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.util.Map;

public class NotEnoughKeysCoremod implements IFMLLoadingPlugin {

    public NotEnoughKeysCoremod() {
        MixinBootstrap.init();
        MixinEnvironment env = MixinEnvironment.getDefaultEnvironment();
        env.addConfiguration("mixins.notenoughkeys.json");
    }
    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return "modwarriors.notenoughkeys.NotEnoughKeys";
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return "modwarriors.notenoughkeys.coremod.NotEnoughKeysAccessTransformer";
    }
}
