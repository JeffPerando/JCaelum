
package com.elusivehawk.engine.assets;

import java.io.File;
import java.util.List;
import java.util.Map;
import com.elusivehawk.engine.math.MathHelper;
import com.elusivehawk.engine.render.Color;
import com.elusivehawk.engine.render.EnumColorFormat;
import com.elusivehawk.engine.render.IllumConst;
import com.elusivehawk.engine.util.TextParser;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ReaderMTL implements IAssetReader
{
	public static final String[] PREFIXES = {"newmtl", "Ka", "Kd", "Ks", "Ns", "d", "Tr", "illum"};
	
	@Override
	public Asset readAsset(AssetManager mgr, File file)
	{
		List<String> lines = TextParser.read(file);
		
		if (lines.isEmpty())
		{
			return null;
		}
		
		Map<String, String> mtl = Maps.newHashMap();
		int i = -1;
		
		for (String line : lines)
		{
			line = line.trim();
			
			if (line.startsWith("#"))
			{
				continue;
			}
			
			if (line.indexOf(' ') == -1)
			{
				continue;
			}
			
			for (String prefix : PREFIXES)
			{
				if (line.startsWith(prefix) && !mtl.containsKey(prefix))
				{
					i = line.indexOf("#");
					mtl.put(prefix, line.substring(prefix.length() + 1, i == -1 ? line.length() : i).trim());
					continue;
				}
				
			}
			
		}
		
		Color[] colors = new Color[3];
		
		String name = mtl.get(PREFIXES[0]);
		
		String str = null;
		
		for (int c = 0; c < 3; c++)
		{
			str = mtl.get(PREFIXES[c + 1]);
			
			if (str == null)
			{
				colors[c] = new Color(EnumColorFormat.RGBA);
			}
			else
			{
				String[] strFl = str.split(" ");
				
				if (strFl == null || strFl.length != 3)
				{
					return null;
				}
				
				colors[c] = new Color(EnumColorFormat.RGBA, Float.parseFloat(strFl[0]), Float.parseFloat(strFl[1]), Float.parseFloat(strFl[2]), 0f);
				
			}
			
		}
		
		str = mtl.get(PREFIXES[4]);
		
		if (str == null || "".equals(str))
		{
			return null;
		}
		
		float spec_coef = MathHelper.clamp(Float.parseFloat(str), 0f, 1000f);
		
		str = mtl.get(PREFIXES[5]);
		
		if (str == null || "".equals(str))
		{
			str = mtl.get(PREFIXES[6]);
			
		}
		
		if (str == null || "".equals(str))
		{
			return null;
		}
		
		float transparency = Float.parseFloat(str);
		
		str = mtl.get(PREFIXES[7]);
		
		if (str == null || "".equals(str))
		{
			return null;
		}
		
		short illum = IllumConst.ILLUM_ENUMS[MathHelper.clamp(Integer.parseInt(str), 0, 10)];
		
		return /*FIXME*/null;
	}
	
}
