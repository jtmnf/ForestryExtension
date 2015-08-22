package jtmnf.forestryextension.blocks;

import jtmnf.forestryextension.ForestryExtension;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockAux extends Block {
    public BlockAux() {
        super(Material.rock);

        this.setCreativeTab(ForestryExtension.ForestryExtensionTab.tab);
        this.setHardness(1F);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s%s", ForestryExtension.MODID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName){
        return unlocalizedName.substring(unlocalizedName.toLowerCase().indexOf(".")+1);
    }
}
