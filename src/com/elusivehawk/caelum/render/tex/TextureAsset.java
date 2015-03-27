
package com.elusivehawk.caelum.render.tex;

import java.io.DataInputStream;
import java.util.List;
import com.elusivehawk.caelum.CaelumEngine;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.assets.AssetManager;
import com.elusivehawk.caelum.assets.IAsset;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TextureAsset extends Texture implements IAsset
{
	private final String path;
	
	private boolean read = false;
	
	@SuppressWarnings("unqualified-field-access")
	public TextureAsset(String filepath)
	{
		path = filepath;
		
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void read(DataInputStream in) throws Throwable
	{
		AssetManager mgr = CaelumEngine.assets();
		
		Object read = mgr.readObjectForAsset(this, in);
		
		if (read == null)
		{
			throw new NullPointerException(String.format("Cannot use null for jack, diddly, or squat in texture asset %s", this));
		}
		
		List<ILegibleImage> imgs = Lists.newArrayList();
		
		if (read instanceof ILegibleImage)
		{
			imgs.add((ILegibleImage)read);
			
		}
		else if (read instanceof List<?>)
		{
			imgs.addAll((List<ILegibleImage>)read);
			
		}
		else throw new CaelumException("Unusable return type for texture \"%s\": %s", this.getLocation(), read);
		
		ILegibleImage[] srcs = imgs.toArray(new ILegibleImage[imgs.size()]);
		
		synchronized (this)
		{
			this.sources = srcs;
			this.read = true;
			
		}
		
	}
	
	@Override
	public void onDuplicateFound(IAsset a)
	{
		assert a instanceof TextureAsset;
		
		TextureAsset ta = (TextureAsset)a;
		
		this.sources = ta.sources;
		this.frames = ta.frames;
		this.frame = ta.frame;
		this.id = ta.id;
		
	}
	
}
