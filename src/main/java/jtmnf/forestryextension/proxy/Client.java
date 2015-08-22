package jtmnf.forestryextension.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import jtmnf.forestryextension.register.BlockRegister;
import jtmnf.forestryextension.render.CentrifugeRender;
import jtmnf.forestryextension.render.CentrifugeRenderInventory;
import jtmnf.forestryextension.render.ItemRendererCentrifuge;
import jtmnf.forestryextension.tileentity.CentrifugeTileEntity;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class Client extends Common {
    public static void registerRender() {
        TileEntitySpecialRenderer tileEntitySpecialRenderer = new CentrifugeRender();
        TileEntitySpecialRenderer tileEntitySpecialRendererInventory = new CentrifugeRenderInventory();

        {
            /* RENDER BLOCK IN WORLD*/
            ClientRegistry.bindTileEntitySpecialRenderer(CentrifugeTileEntity.class, tileEntitySpecialRenderer);

            /* RENDER BLOCK IN INVENTORY */
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegister.centrifugeExtension), new ItemRendererCentrifuge(tileEntitySpecialRendererInventory, new CentrifugeTileEntity()));
        }
    }

    public static void registerThings(){;
    }
}
