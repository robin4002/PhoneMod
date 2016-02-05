package fr.mcnanotech.robin4002.phonemod;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPhone extends Item
{
    private String[] type = new String[] {"nokia", "iphone", "brokenIphone"};

    public int getMetadata(int metadata)
    {
        return metadata;
    }

    public String getUnlocalizedName(ItemStack stack)
    {
        int metadata = stack.getItemDamage();
        if(metadata > type.length || metadata < 0)
        {
            metadata = 0;
        }
        return super.getUnlocalizedName() + "." + type[metadata];
    }

    @Override
    public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
    {
        if(stack.getItemDamage() == 0)
            return new ModelResourceLocation(PhoneMod.MODID + ":item_3310", "inventory");
        else if(stack.getItemDamage() == 1)
            return new ModelResourceLocation(PhoneMod.MODID + ":item_iphone_6s", "inventory");
        else
            return new ModelResourceLocation(PhoneMod.MODID + ":item_broken_iphone_6s", "inventory");
    }

    public boolean hasCustomEntity(ItemStack stack)
    {
        return stack.getItemDamage() == 0 || stack.getItemDamage() == 1;
    }

    public Entity createEntity(World world, Entity location, ItemStack itemstack)
    {
        CustomEntityItem entityItem = new CustomEntityItem(world, location.posX, location.posY, location.posZ, itemstack);
        entityItem.setPickupDelay(20);
        entityItem.motionX = location.motionX;
        entityItem.motionY = location.motionY;
        entityItem.motionZ = location.motionZ;
        return entityItem;
    }
}