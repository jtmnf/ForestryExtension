package jtmnf.forestryextension.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jtmnf.forestryextension.containers.slots.SlotZero;
import jtmnf.forestryextension.tileentity.CentrifugeTileEntity;
import jtmnf.forestryextension.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CentrifugeContainer extends Container {
    private CentrifugeTileEntity machine;

    private int time;
    private int energyStored;

    public CentrifugeContainer(InventoryPlayer inventoryPlayer, CentrifugeTileEntity centrifugeTileEntity) {
        this.machine = centrifugeTileEntity;

        for(int i = 0; i < 9; ++i){
            addSlotToContainer(new Slot(inventoryPlayer, i, 20 + 18*i, 107));
        }

        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 9; ++j){
                addSlotToContainer(new Slot(inventoryPlayer, j + i*9 + 9, 20 + 18*j, 49 + i*18));
            }
        }

        addSlotToContainer(new Slot(machine, 0, 20, 18));

        addSlotToContainer(new SlotZero(machine, 1, 56, 8));
        addSlotToContainer(new SlotZero(machine, 2, 75, 8));
        addSlotToContainer(new SlotZero(machine, 3, 94, 8));

        addSlotToContainer(new SlotZero(machine, 4, 56, 27));
        addSlotToContainer(new SlotZero(machine, 5, 75, 27));
        addSlotToContainer(new SlotZero(machine, 6, 94, 27));

        addSlotToContainer(new Slot(machine, 7, 164, 8));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return machine.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int i) {
        return null;
    }

    @Override
    public void addCraftingToCrafters(ICrafting p_75132_1_) {
        super.addCraftingToCrafters(p_75132_1_);

        p_75132_1_.sendProgressBarUpdate(this, 0, this.machine.time);
        p_75132_1_.sendProgressBarUpdate(this, 1, this.machine.energyStorage.getEnergyStored());
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.time != this.machine.time) {
                icrafting.sendProgressBarUpdate(this, 0, this.machine.time);
            }

            if(this.energyStored != this.machine.energyStorage.getEnergyStored()){
                icrafting.sendProgressBarUpdate(this, 1, this.machine.energyStorage.getEnergyStored());
            }
        }

        this.time = this.machine.time;
        this.energyStored = this.machine.energyStorage.getEnergyStored();
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int p_75137_1_, int p_75137_2_) {
        super.updateProgressBar(p_75137_1_, p_75137_2_);
        if (p_75137_1_ == 0) {
            this.machine.time = p_75137_2_;
        }

        if (p_75137_1_ == 1) {
            this.machine.setEnergyStorage(p_75137_2_);
        }
    }
}
