package edivad.dimstorage.api;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;

public class Frequency {

	private String owner;
	private int channel;

	public Frequency()
	{
		this(1);
	}
	
	public Frequency(int channel)
	{
		this("public", channel);
	}
	
	public Frequency(String owner, int channel)
	{
		this.owner = owner;
		this.channel = channel;
	}

	public Frequency setOwner(String owner)
	{
		this.owner = owner;
		return this;
	}

	public String getOwner()
	{
		return owner;
	}

	public boolean hasOwner()
	{
		return !owner.equals("public");
	}

	public Frequency setChannel(int channel)
	{
		this.channel = channel;
		return this;
	}

	public int getChannel()
	{
		return channel;
	}

	public Frequency(NBTTagCompound tagCompound)
	{
		read_internal(tagCompound);
	}

	protected Frequency read_internal(NBTTagCompound tagCompound)
	{
		owner = tagCompound.getString("owner");
		channel = tagCompound.getInteger("channel");
		//Useful at the moment to avoid console spam
		if(!Loader.isModLoaded("waila"))
			System.out.println("read_internal: " + this);
		return this;
	}

	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		write_internal(tagCompound);
		return tagCompound;
	}

	protected NBTTagCompound write_internal(NBTTagCompound tagCompound)
	{
		tagCompound.setString("owner", owner);
		tagCompound.setInteger("channel", channel);
		return tagCompound;
	}

	public static Frequency readFromStack(ItemStack stack)
	{
		if(stack.hasTagCompound())
		{
			NBTTagCompound stackTag = stack.getTagCompound();
			if(stackTag.hasKey("Frequency"))
			{
				return new Frequency(stackTag.getCompoundTag("Frequency"));
			}
		}
		return new Frequency();
	}

	public ItemStack writeToStack(ItemStack stack)
	{
		NBTTagCompound tagCompound;
		if(!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
			tagCompound = stack.getTagCompound();
		}
		else
			tagCompound = stack.getTagCompound();
		tagCompound.setTag("Frequency", write_internal(new NBTTagCompound()));
		return stack;
	}

	@Override
	public String toString()
	{
		return "owner=" + owner + ",channel=" + channel;
	}

	public Frequency copy()
	{
		return new Frequency(owner, channel);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Frequency))
			return false;

		Frequency f = (Frequency) obj;
		return (f.channel == this.channel && f.owner == this.owner);
	}

	public Frequency set(Frequency frequency)
	{
		this.owner = frequency.owner;
		this.channel = frequency.channel;
		return this;
	}
}
