package fr.mcnanotech.robin4002.phonemod;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CustomEntityItem extends EntityItem
{
    public CustomEntityItem(World worldIn, double x, double y, double z, ItemStack stack)
    {
        super(worldIn, x, y, z, stack);
        this.lifespan = Integer.MAX_VALUE;
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {
        if(this.getEntityItem().getItemDamage() == 0)
        {
            BlockPos pos = new BlockPos(this.posX, this.posY - 1, this.posZ);
            IBlockState state = this.worldObj.getBlockState(pos);
            if(state.getBlock().getBlockHardness(this.worldObj, pos) >= 0.0F && state.getBlock().getBlockHardness(this.worldObj, pos) <= distance / 5.0F)
            {
                this.worldObj.setBlockToAir(pos);
                this.entityDropItem(new ItemStack(state.getBlock().getItemDropped(state, this.rand, 0), 1, state.getBlock().damageDropped(state)), 0.5F);
                this.worldObj.playSoundAtEntity(this, state.getBlock().stepSound.getBreakSound(), state.getBlock().stepSound.getVolume(), state.getBlock().stepSound.getFrequency());
            }
            PhoneMod.network.sendToAllAround(new PacketParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY, this.posZ, 0.1D, 0.1D, 0.1D, new int[] {Block.getStateId(state)}), new NetworkRegistry.TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32));
        }
        else if(this.getEntityItem().getItemDamage() == 1)
        {
            if(distance > 1.5F)
            {
                this.setEntityItemStack(new ItemStack(PhoneMod.phone, this.getEntityItem().stackSize, 2));
                PhoneMod.network.sendToAllAround(new PacketParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5D) * 0.4, this.posY + (this.rand.nextFloat() - 0.5D) * 0.4, this.posZ + (this.rand.nextFloat() - 0.5D) * 0.4, this.rand.nextFloat() * 3, this.rand.nextFloat() * 3, this.rand.nextFloat() * 3, new int[] {Block.getIdFromBlock(Blocks.glass)}), new NetworkRegistry.TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32));

                this.worldObj.playSoundAtEntity(this, Blocks.glass.stepSound.getBreakSound(), Blocks.glass.stepSound.getVolume(), Blocks.glass.stepSound.getFrequency());
            }
        }
    }

    public boolean isEntityInvulnerable(DamageSource source)
    {
        return super.isEntityInvulnerable(source) || (this.getEntityItem() != null && this.getEntityItem().getItemDamage() == 0);
    }
}