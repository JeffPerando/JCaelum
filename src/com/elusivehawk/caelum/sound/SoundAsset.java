
package com.elusivehawk.caelum.sound;

import java.io.DataInputStream;
import com.elusivehawk.caelum.assets.IAsset;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class SoundAsset extends SoundBuffer implements IAsset
{
	private final String path;
	
	private boolean read = false;
	
	@SuppressWarnings("unqualified-field-access")
	public SoundAsset(SoundClient sClient, String loc)
	{
		super(sClient);
		
		assert loc != null;
		
		path = loc;
		
	}
	
	@Override
	public int getId()
	{
		if (!this.isRead())
		{
			return 0;
		}
		
		return super.getId();
	}
	
	@Override
	public String getLocation()
	{
		return this.path;
	}
	
	@Override
	public boolean isRead()
	{
		return this.read;
	}
	
	@Override
	public void read(DataInputStream in) throws Throwable
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onDuplicateFound(IAsset asset)
	{
		// TODO Auto-generated method stub
		
	}
	
}
