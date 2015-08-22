package jtmnf.forestryextension.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRendererCentrifuge implements IItemRenderer {

    private TileEntity tileEntity;
    private TileEntitySpecialRenderer tileEntitySpecialRenderer;

    public ItemRendererCentrifuge(TileEntitySpecialRenderer tileEntitySpecialRenderer, TileEntity tileEntity){
        this.tileEntity = tileEntity;
        this.tileEntitySpecialRenderer = tileEntitySpecialRenderer;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if(type == ItemRenderType.ENTITY){
            GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
        }

        this.tileEntitySpecialRenderer.renderTileEntityAt(this.tileEntity, 0.0D, 0.0D, 0.0D, 0.0F);
    }
}
