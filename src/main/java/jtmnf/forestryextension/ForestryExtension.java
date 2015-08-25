package jtmnf.forestryextension;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jtmnf.forestryextension.proxy.Client;
import jtmnf.forestryextension.proxy.IProxy;
import jtmnf.forestryextension.register.BlockRegister;
import jtmnf.forestryextension.register.InterfaceRegister;
import jtmnf.forestryextension.register.TileEntityRegister;
import jtmnf.forestryextension.util.LogHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

@Mod(modid = ForestryExtension.MODID, name = ForestryExtension.MODNAME, version = ForestryExtension.VERSION)
public class ForestryExtension {
    public static final String MODID = "forestryextension";
    public static final String MODNAME = "ForestryExtension";
    public static final String VERSION = "1.0.0";
    public static final String CLIENTPROXY = "jtmnf.forestryextension.proxy.Client";
    public static final String SERVERPROXY = "jtmnf.forestryextension.proxy.Server";

    public static boolean isForestry = false;

    @Mod.Instance(ForestryExtension.MODID)
    public static ForestryExtension instance;

    @SidedProxy(clientSide = ForestryExtension.CLIENTPROXY, serverSide = ForestryExtension.SERVERPROXY)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        BlockRegister.register();
        TileEntityRegister.register();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        InterfaceRegister.register();

        proxy.registerRender();
    }

    @Mod.EventHandler
    public void posInit(FMLPostInitializationEvent event){
        if(Loader.isModLoaded("Forestry")){
            isForestry = true;
            LogHelper.info("Forestry Loaded");
        }
        else{
            isForestry = false;
            LogHelper.info("What? Starting without Forestry?");
        }
    }

    public static class ForestryExtensionTab {
        public static final CreativeTabs tab = new CreativeTabs(ForestryExtension.MODID) {
            @Override
            public Item getTabIconItem() {
                return Items.arrow;
            }
        };
    }
}
