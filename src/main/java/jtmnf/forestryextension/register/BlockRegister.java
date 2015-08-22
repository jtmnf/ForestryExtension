package jtmnf.forestryextension.register;

import cpw.mods.fml.common.registry.GameRegistry;
import jtmnf.forestryextension.blocks.BlockAux;
import jtmnf.forestryextension.blocks.CentrifugeExtension;

public class BlockRegister {

    public static BlockAux centrifugeExtension = new CentrifugeExtension();

    public static void register(){
        GameRegistry.registerBlock(centrifugeExtension, "centrifugeExtension");
    }
}
