package jtmnf.forestryextension.tileentity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.recipes.ICentrifugeRecipe;
import forestry.api.recipes.RecipeManagers;
import jtmnf.forestryextension.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class CentrifugeTileEntity extends TileEntity implements ISidedInventory, ICentrifugeRecipe, IEnergyHandler {
    private ItemStack[] items;
    private boolean isCombThere = true;
    private boolean active = false;

    public int time;
    public static float TIME_TO_PROCESS_COMBS = 24 * 8; //24 ticks per second * 8 seconds = 192 ticks;
    public static int COST_PER_COMB = 3500;

    public EnergyStorage energyStorage;
    public boolean isEnergy = false;

    /* =================================================================================== */
    /* =================================== Constructor =================================== */
    /* =================================================================================== */
    public CentrifugeTileEntity() {
        items = new ItemStack[8];

        energyStorage = new EnergyStorage(1000000);
        energyStorage.setMaxReceive(10000);
        energyStorage.setEnergyStored(0);
    }

    /* ================================================================================== */
    /* =================================== IInventory =================================== */
    /* ================================================================================== */
    @Override
    public int getSizeInventory() {
        return items.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return items[slot];
    }

    @Override
    public ItemStack decrStackSize(int i, int count) {
        ItemStack itemstack = getStackInSlot(i);

        if (itemstack != null) {
            if (itemstack.stackSize <= count) {
                setInventorySlotContents(i, null);
            }else{
                itemstack = itemstack.splitStack(count);
                markDirty();
            }
        }

        return itemstack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.items[slot] != null)
        {
            ItemStack itemstack = this.items[slot];
            this.items[slot] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        items[slot] = itemStack;

        if(itemStack != null && itemStack.stackSize > getInventoryStackLimit()){
            itemStack.stackSize = getInventoryStackLimit();
        }

        markDirty();
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return true;
    }

    @Override
    public void updateEntity() {
        if(getStackInSlot(0) != null && isCombThere && !worldObj.isRemote) {
            setTimeToProcessCombs();

            ItemStack itemStack = getStackInSlot(0);
            Object[] products = getProductsByComb(itemStack);

            if((COST_PER_COMB * itemStack.stackSize) > energyStorage.getEnergyStored() && !active){
                isEnergy = true;
                isCombThere = false;
                return ;
            }
            else{
                isEnergy = false;
                active = true;
            }

            if (products != null) {
                if ((time / TIME_TO_PROCESS_COMBS) != 1) {
                    time++;

                    active = true;
                    energyStorage.setEnergyStored(energyStorage.getEnergyStored() - Math.round((COST_PER_COMB * itemStack.stackSize)/TIME_TO_PROCESS_COMBS));
                } else {
                    for (int i = 0; i < products.length; ++i) {
                        active = false;

                        boolean isInserted = false;

                        boolean isEverythingOk = simulate((ItemStack) products[i], itemStack.stackSize);
                        if (!isEverythingOk || products == null) {
                            isCombThere = false;
                            return ;
                        }

                        for (int j = 1; j < getSizeInventory() && !isInserted; ++j) {
                            ItemStack product = (ItemStack) products[i];

                            if (getStackInSlot(j) == null) {
                                setInventorySlotContents(j, new ItemStack(product.getItem(), itemStack.stackSize));
                                isInserted = true;
                            } else {
                                ItemStack stack = getStackInSlot(j);

                                if (stack.getUnlocalizedName().equals(product.getUnlocalizedName()) && stack.stackSize < 64) {
                                    int stackSize = stack.stackSize;

                                    if ((stackSize + itemStack.stackSize) > 64) {
                                        setInventorySlotContents(j, new ItemStack(product.getItem(), 64));

                                        for (int z = 1; z < getSizeInventory() && !isInserted; ++z) {
                                            if (getStackInSlot(z) == null) {
                                                setInventorySlotContents(z, new ItemStack(product.getItem(), (stackSize + itemStack.stackSize) - 64));
                                                isInserted = true;
                                            }
                                        }
                                    } else {
                                        setInventorySlotContents(j, new ItemStack(product.getItem(), (stackSize + itemStack.stackSize)));
                                        isInserted = true;
                                    }
                                }
                            }
                        }
                    }

                    setInventorySlotContents(0, null);
                    isCombThere = false;

                    markDirty();
                }
            }
        }
        else if(getStackInSlot(0) == null && !worldObj.isRemote){
            isCombThere = true;
        }
    }

    /* ================================================================================= */
    /* =================================== NBT Stuff =================================== */
    /* ================================================================================= */
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        NBTTagList items = new NBTTagList();

        for(int i = 0; i < getSizeInventory(); ++i){
            ItemStack itemStack = getStackInSlot(i);

            if(itemStack != null){
                NBTTagCompound item = new NBTTagCompound();
                item.setByte("Slot", (byte) i);

                itemStack.writeToNBT(item);
                items.appendTag(item);
            }
        }

        compound.setTag("Items", items);
        energyStorage.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        NBTTagList items = compound.getTagList("Items", compound.getId());

        for (int i = 0; i < items.tagCount(); i++) {
            NBTTagCompound item = items.getCompoundTagAt(i);

            int slot = item.getByte("Slot");

            if(slot >= 0 && slot < getSizeInventory()){
                setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
            }
        }

        energyStorage.readFromNBT(compound);
    }

    /* ================================================================================= */
    /* =================================== markDirty =================================== */
    /* ================================================================================= */
    @Override
    public void markDirty() {
        if(getStackInSlot(0) == null){
            time = 0;
        }
        isCombThere = true;
    }

    /* ========================================================================================= */
    /* =================================== ICentrifugeRecipe =================================== */
    /* ========================================================================================= */
    @Override
    public ItemStack getInput() {
        return null;
    }

    @Override
    public int getProcessingTime() {
        return 0;
    }

    @Override
    public Collection<ItemStack> getProducts(Random random) {
        return null;
    }

    @Override
    public Map<ItemStack, Float> getAllProducts() {
        return null;
    }

    /* ======================================================================================= */
    /* =================================== ProperFunctions =================================== */
    /* ======================================================================================= */
    private Object[] getProductsByComb(ItemStack itemStack){
         Iterator<Map.Entry<Object[], Object[]>> iterator = RecipeManagers.centrifugeManager.getRecipes().entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Object[], Object[]> aux = iterator.next();

            ItemStack product = (ItemStack) aux.getKey()[0];

            if(product.getUnlocalizedName().equals(itemStack.getUnlocalizedName())){
                return aux.getValue();
            }
        }

        return null;
    }

    /**
     * @param product item to be analyzed
     * @return true if can proceed correctly, false if there is something wrong
     */
    private boolean simulate(ItemStack product, int number){
        for(int i = 1; i < getSizeInventory(); ++i){
            ItemStack itemStack = getStackInSlot(i);

            if(itemStack == null){
                return true;
            }
            else{
                if(itemStack.getUnlocalizedName().equals(product.getUnlocalizedName())){
                    if((itemStack.stackSize + number) <= 64){
                        return true;
                    }
                    else{
                        for(int z = 1; z < getSizeInventory(); ++z){
                            if(getStackInSlot(z) == null){
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public void setTimeToProcessCombs(){
        if(getStackInSlot(7) != null){
            TIME_TO_PROCESS_COMBS = 24 * 4;
        }
    }

    /* ====================================================================================== */
    /* =================================== IEnergyHandler =================================== */
    /* ====================================================================================== */

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return energyStorage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }


    /* SIDED INVENTORY */
    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        return false;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
        return false;
    }
}
