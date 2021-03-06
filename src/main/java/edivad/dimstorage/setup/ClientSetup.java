package edivad.dimstorage.setup;

import edivad.dimstorage.Main;
import edivad.dimstorage.client.render.tile.RenderTileDimChest;
import edivad.dimstorage.client.screen.ScreenDimChest;
import edivad.dimstorage.client.screen.ScreenDimTablet;
import edivad.dimstorage.tile.TileEntityDimChest;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

	public static void init(final FMLClientSetupEvent event)
	{
		//Version checker
		MinecraftForge.EVENT_BUS.register(EventHandler.INSTANCE);

		//Special render & GUI
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDimChest.class, new RenderTileDimChest());
		ScreenManager.registerFactory(Registration.DIMCHEST_CONTAINER.get(), ScreenDimChest::new);
		ScreenManager.registerFactory(Registration.DIMPAD_CONTAINER.get(), ScreenDimTablet::new);
	}
}