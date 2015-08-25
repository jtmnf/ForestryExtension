package jtmnf.forestryextension.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jtmnf.forestryextension.models.ModelCentrifuge;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class CentrifugeRender extends TileEntitySpecialRenderer{

    ResourceLocation texture = new ResourceLocation("forestryextension", "textures/models/centrifuge.png");
    private final ModelCentrifuge modelCentrifuge;

    public CentrifugeRender(){
        this.modelCentrifuge = new ModelCentrifuge();
    }

    /* In: http://www.minecraftforge.net/wiki/Rendering_a_Techne_Model_as_a_Block */
    @Override
    @SideOnly(Side.CLIENT)
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale) {
        GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

            if(tileEntity != null){
                int aux = tileEntity.getBlockMetadata();
                int rotation = 0;

                if(aux == 2){
                    rotation = 360;
                }
                if(aux == 3){
                    rotation = 180;
                }
                if(aux == 4){
                    rotation = -90;
                }
                if(aux == 5){
                    rotation = 90;
                }

                GL11.glRotatef(rotation, 0F, 1F, 0F);
            }

            this.bindTexture(texture);

            GL11.glPushMatrix();
                this.modelCentrifuge.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
            GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    @SideOnly(Side.CLIENT)
    private void adjustLightFixture(World world, int i, int j, int k, Block block) {
        Tessellator tess = Tessellator.instance;
        float brightness = block.getLightValue(world, i, j, k);
        int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int modulousModifier = skyLight % 65536;
        int divModifier = skyLight / 65536;
        tess.setColorOpaque_F(brightness, brightness, brightness);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) modulousModifier, divModifier);
    }
}

