package edivad.dimstorage.compat;

import edivad.dimstorage.compat.top.TOPCompatibility;
import net.minecraftforge.fml.ModList;

public class MainCompatHandler {

	public static void registerTOP()
	{
		if(ModList.get().isLoaded("theoneprobe"))
		{
			TOPCompatibility.register();
		}
	}
}
