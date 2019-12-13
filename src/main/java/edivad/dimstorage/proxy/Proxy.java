package edivad.dimstorage.proxy;

import com.google.common.util.concurrent.ListenableFuture;

import codechicken.lib.packet.PacketCustom;
import edivad.dimstorage.Main;
import edivad.dimstorage.ModBlocks;
import edivad.dimstorage.ModItems;
import edivad.dimstorage.manager.DimStorageManager;
import edivad.dimstorage.network.DimStorageSPH;
import edivad.dimstorage.network.test.PacketHandler;
import edivad.dimstorage.plugin.DimChestPlugin;
import edivad.dimstorage.plugin.DimTankPlugin;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod.EventBusSubscriber
public class Proxy {

	public void preInit(FMLPreInitializationEvent e)
	{
		DimStorageManager.registerPlugin(new DimChestPlugin());
		DimStorageManager.registerPlugin(new DimTankPlugin());
		MinecraftForge.EVENT_BUS.register(new DimStorageManager.DimStorageSaveHandler());
	}

	public void init(FMLInitializationEvent e)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
		PacketCustom.assignHandler(DimStorageSPH.channel, new DimStorageSPH());
		PacketHandler.init();
	}

	public void postInit(FMLPostInitializationEvent e)
	{

	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		ModBlocks.register(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		ModItems.register(event.getRegistry());
	}

	public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule)
	{
		throw new IllegalStateException("This should only be called from client side");
	}

	public EntityPlayer getClientPlayer()
	{
		throw new IllegalStateException("This should only be called from client side");
	}
}
