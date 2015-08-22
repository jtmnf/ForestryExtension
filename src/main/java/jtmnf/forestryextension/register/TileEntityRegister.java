package jtmnf.forestryextension.register;

import cpw.mods.fml.common.registry.GameRegistry;
import jtmnf.forestryextension.tileentity.CentrifugeTileEntity;

public class TileEntityRegister {
    public static void register(){
        GameRegistry.registerTileEntity(CentrifugeTileEntity.class, "centrifugeTileEntity");
    }
}
