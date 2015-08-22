package jtmnf.forestryextension.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import jtmnf.forestryextension.ForestryExtension;
import jtmnf.forestryextension.containers.CentrifugeContainer;
import jtmnf.forestryextension.interfaces.InterfaceCentrifuge;
import jtmnf.forestryextension.tileentity.CentrifugeTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class InterfaceHandler implements IGuiHandler {

    public InterfaceHandler(){
        NetworkRegistry.INSTANCE.registerGuiHandler(ForestryExtension.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID){
            case 0:
                TileEntity tileEntity = world.getTileEntity(x, y, z);

                if(tileEntity != null && tileEntity instanceof CentrifugeTileEntity){
                    return new CentrifugeContainer(player.inventory, ((CentrifugeTileEntity) tileEntity));
                }
                break;
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID){
            case 0:
                TileEntity tileEntity = world.getTileEntity(x, y, z);

                if(tileEntity != null && tileEntity instanceof CentrifugeTileEntity){
                    return new InterfaceCentrifuge(player.inventory, ((CentrifugeTileEntity) tileEntity));
                }
                break;
        }

        return null;
    }
}
