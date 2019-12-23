package edivad.dimstorage.network;

import codechicken.lib.packet.ICustomPacketHandler.IClientPacketHandler;
import codechicken.lib.packet.PacketCustom;
import edivad.dimstorage.Main;
import edivad.dimstorage.api.Frequency;
import edivad.dimstorage.manager.DimStorageManager;
import edivad.dimstorage.storage.DimChestStorage;
import edivad.dimstorage.tile.TileEntityDimTank;
import edivad.dimstorage.tile.TileFrequencyOwner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class DimStorageCPH implements IClientPacketHandler {

	public static final String channel = Main.MODID;

	@Override
	public void handlePacket(PacketCustom packet, Minecraft mc, INetHandlerPlayClient handler)
	{

		switch (packet.getType())
		{
			case 1:
				handleTilePacket(mc.world, packet, packet.readPos());
				System.out.println("readFromPacket");
				break;
			case 2:
				//openGui(mc.world, mc.player.inventory, packet);
				System.out.println("openGui");
				break;
			case 3:
				Frequency freq = Frequency.readFromPacket(packet);
				((DimChestStorage) DimStorageManager.instance(true).getStorage(freq, "item")).setClientOpen(packet.readBoolean() ? 1 : 0);
				System.out.println("Chest open on the frequency: " + freq);
				break;
			case 4:
				TankSynchroniser.syncClient(Frequency.readFromPacket(packet), packet.readFluidStack());
				break;
			case 5:
			case 6:
				handleTankTilePacket(mc.world, packet.readPos(), packet);
				break;
			default:
				System.out.println(packet.getType());
				break;
		}
	}

	private void handleTankTilePacket(WorldClient world, BlockPos pos, PacketCustom packet)
	{
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileEntityDimTank)
		{
			((TileEntityDimTank) tile).sync(packet);
		}
	}

	private void handleTilePacket(WorldClient world, PacketCustom packet, BlockPos pos)
	{
		TileEntity tile = world.getTileEntity(pos);

		if(tile instanceof TileFrequencyOwner)
		{
			((TileFrequencyOwner) tile).readFromPacket(packet);
		}
	}
}
