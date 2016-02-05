package fr.mcnanotech.robin4002.phonemod;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketParticle implements IMessage
{
    private EnumParticleTypes particleType;
    private double xCoord;
    private double yCoord;
    private double zCoord;
    private double xOffset;
    private double yOffset;
    private double zOffset;
    private int[] custom;

    public PacketParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... custom)
    {
        this.particleType = particleType;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.custom = custom;
    }

    public PacketParticle()
    {

    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.particleType = EnumParticleTypes.values()[buf.readInt()];
        this.xCoord = buf.readDouble();
        this.yCoord = buf.readDouble();
        this.zCoord = buf.readDouble();
        this.xOffset = buf.readDouble();
        this.yOffset = buf.readDouble();
        this.zOffset = buf.readDouble();
        this.custom = new int[buf.readInt()];
        for(int i = 0; i < custom.length; i++)
        {
            custom[i] = buf.readInt();
        }

    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.particleType.ordinal());
        buf.writeDouble(this.xCoord);
        buf.writeDouble(this.yCoord);
        buf.writeDouble(this.zCoord);
        buf.writeDouble(this.xOffset);
        buf.writeDouble(this.yOffset);
        buf.writeDouble(this.zOffset);
        buf.writeInt(this.custom.length);
        for(int i : this.custom)
        {
            buf.writeInt(i);
        }
    }

    public static class Handler implements IMessageHandler<PacketParticle, IMessage>
    {
        @Override
        public IMessage onMessage(PacketParticle message, MessageContext ctx)
        {
            for(int i = 0; i < 10; i++)
            {
                FMLClientHandler.instance().getWorldClient().spawnParticle(message.particleType, message.xCoord, message.yCoord, message.zCoord, message.xOffset, message.yOffset, message.zOffset, message.custom);
            }
            return null;
        }
    }
}