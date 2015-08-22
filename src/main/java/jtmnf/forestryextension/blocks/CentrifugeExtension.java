package jtmnf.forestryextension.blocks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import jtmnf.forestryextension.ForestryExtension;
import jtmnf.forestryextension.tileentity.CentrifugeTileEntity;
import jtmnf.forestryextension.util.LogHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CentrifugeExtension extends BlockAux{
    public CentrifugeExtension() {
        super();

        this.setBlockName("centrifugeExtension");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float side, float p_149727_8_, float p_149727_9_) {
        if(!world.isRemote){
            FMLNetworkHandler.openGui(player, ForestryExtension.instance, 0, world, x, y, z);
        }

        return true;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityplayer, ItemStack itemstack){
        int l = MathHelper.floor_double((double) (entityplayer.rotationYaw * 4.0F / 360F) + 0.5D) & 3;

        if(l == 0){
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        else if(l == 1){
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
        else if(l == 2){
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        else if(l == 3){
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }

    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new CentrifugeTileEntity();
    }

    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }
}
