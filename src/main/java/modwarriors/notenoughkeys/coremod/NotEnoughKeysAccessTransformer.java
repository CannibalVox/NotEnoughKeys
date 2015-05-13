package modwarriors.notenoughkeys.coremod;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;

import java.io.IOException;

public class NotEnoughKeysAccessTransformer extends AccessTransformer {
    public NotEnoughKeysAccessTransformer() throws IOException {
        super("notenoughkeys_at.cfg");
    }
}
