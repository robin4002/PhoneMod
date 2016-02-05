package fr.mcnanotech.robin4002.phonemod;

import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = PhoneMod.MODID, name = "Phone Mod", version = "1.0.0")
public class PhoneMod
{
    public static final String MODID = "phonemod";

    @Instance(PhoneMod.MODID)
    public static PhoneMod instance;

    public static Item phone;
    
    public static SimpleNetworkWrapper network;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        PhoneMod.phone = new ItemPhone().setUnlocalizedName("phone").setHasSubtypes(true);
        GameRegistry.registerItem(PhoneMod.phone, "phone");

        if(event.getSide().isClient())
        {
            ModelBakery.registerItemVariants(PhoneMod.phone, new ResourceLocation(PhoneMod.MODID, "item_3310"), new ResourceLocation(PhoneMod.MODID, "item_iphone_6s"), new ResourceLocation(PhoneMod.MODID, "item_broken_iphone_6s"));

            ModelLoader.setCustomModelResourceLocation(PhoneMod.phone, 0, new ModelResourceLocation(PhoneMod.MODID + ":item_3310", "inventory"));
            ModelLoader.setCustomModelResourceLocation(PhoneMod.phone, 1, new ModelResourceLocation(PhoneMod.MODID + ":item_iphone_6s", "inventory"));
            ModelLoader.setCustomModelResourceLocation(PhoneMod.phone, 2, new ModelResourceLocation(PhoneMod.MODID + ":item_broken_iphone_6s", "inventory"));

            RenderingRegistry.registerEntityRenderingHandler(CustomEntityItem.class, new RenderEntityItem(FMLClientHandler.instance().getClient().getRenderManager(), FMLClientHandler.instance().getClient().getRenderItem()));
        }

        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
        PhoneMod.network = NetworkRegistry.INSTANCE.newSimpleChannel(PhoneMod.MODID);
        PhoneMod.network.registerMessage(PacketParticle.Handler.class, PacketParticle.class, 0, Side.CLIENT);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        GameRegistry.addRecipe(new ItemStack(PhoneMod.phone, 1, 0), new Object[] {"OOO", "OGO", "BBB", 'O', Blocks.obsidian, 'G', Blocks.glass_pane, 'B', Blocks.stone_button});
        GameRegistry.addRecipe(new ItemStack(PhoneMod.phone, 1, 1), new Object[] {"GGG", "GAG", "GBG", 'G', Blocks.glass_pane, 'A', Items.golden_apple, 'B', Blocks.stone_button});
        GameRegistry.addRecipe(new ItemStack(PhoneMod.phone, 1, 1), new Object[] {"G", "P", 'G', Blocks.glass_pane, 'P', new ItemStack(PhoneMod.phone, 1, 2)});
    }
}