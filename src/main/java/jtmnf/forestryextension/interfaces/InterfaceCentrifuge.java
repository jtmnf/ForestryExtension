package jtmnf.forestryextension.interfaces;

import jtmnf.forestryextension.containers.CentrifugeContainer;
import jtmnf.forestryextension.tileentity.CentrifugeTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class InterfaceCentrifuge extends GuiContainer {

    private final int ARROW_PIXELS = 13;
    private final int ENERGY_PIXELS = 117;

    private CentrifugeTileEntity machine;
    private static final ResourceLocation texture = new ResourceLocation("forestryextension", "textures/gui/centrifuge.png");

    public InterfaceCentrifuge(InventoryPlayer inventoryPlayer, CentrifugeTileEntity centrifugeTileEntity) {
        super(new CentrifugeContainer(inventoryPlayer, centrifugeTileEntity));
        this.machine = centrifugeTileEntity;

        xSize = 188;
        ySize = 131;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int x, int y) {
        GL11.glColor4f(1F, 1F, 1F, 1F);

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        drawWorkingArrow();
        drawEnergyBar();
        drawNoEnergy();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        if (x >= guiLeft + 6 && x <= guiLeft + 15 && y >= guiTop + 6 && y <= guiTop + 122) {
            List list = new ArrayList();
            list.add("Power: "+CentrifugeContainer.energy+"RF");
            this.drawHoveringText(list, x - guiLeft, y - guiTop, fontRendererObj);
        } else {
            // not hovering
        }
    }

    protected void drawWorkingArrow(){
        if(machine.getStackInSlot(0) != null){
            float arrow = (machine.time * 13F) / machine.TIME_TO_PROCESS_COMBS;

            int srcX = 0;
            int srcY = ySize;

            drawTexturedModalRect(guiLeft + 39, guiTop + 21, srcX, srcY, Math.round(arrow), 9);
        }
    }

    protected void drawEnergyBar(){
        float height = (CentrifugeContainer.energy * 117F) / machine.energyStorage.getMaxEnergyStored();

        int srcX = xSize;
        int srcY = ENERGY_PIXELS - Math.round(height);

        drawTexturedModalRect(guiLeft + 6, guiTop + 6 + 117 - Math.round(height), srcX, srcY, 10, Math.round(height));
    }

    protected void drawNoEnergy(){
        if(CentrifugeContainer.isEnergy){
            drawTexturedModalRect(guiLeft + 41, guiTop + 21, 13, ySize, 9, 9);
        }
    }
}
