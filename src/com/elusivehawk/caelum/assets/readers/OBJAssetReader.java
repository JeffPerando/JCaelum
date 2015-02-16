
package com.elusivehawk.caelum.assets.readers;

import java.io.DataInputStream;
import java.nio.FloatBuffer;
import java.util.List;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.assets.Asset;
import com.elusivehawk.caelum.assets.IAssetReader;
import com.elusivehawk.caelum.render.MeshData;
import com.elusivehawk.caelum.render.ModelPoint;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.io.IOHelper;
import com.elusivehawk.util.math.VectorF;
import com.elusivehawk.util.storage.BufferHelper;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class OBJAssetReader implements IAssetReader
{
	@Override
	public Object readAsset(Asset asset, DataInputStream in) throws Throwable
	{
		List<VectorF> positions = Lists.newArrayList(),
				texCoords = Lists.newArrayList(),
				normals = Lists.newArrayList();
		List<ModelPoint> points = Lists.newArrayList();
		List<Integer> indices = Lists.newArrayList();
		
		List<String> txt = IOHelper.readText(in);
		
		for (String line : txt)
		{
			if (line.startsWith("#"))
			{
				continue;
			}
			
			String[] s = line.split(" ");
			
			String prefix = s[0].toLowerCase();
			
			switch (prefix)
			{
				case "v":
				{
					float x = Float.parseFloat(s[1]);
					float y = Float.parseFloat(s[2]);
					float z = Float.parseFloat(s[3]);
					
					positions.add(new VectorF(x, y, z));
					
				} break;
				case "vt":
				{
					float u = Float.parseFloat(s[1]);
					float v = Float.parseFloat(s[2]);
					
					texCoords.add(new VectorF(u, v));
					
				} break;
				case "vn":
				{
					float x = Float.parseFloat(s[1]);
					float y = Float.parseFloat(s[2]);
					float z = Float.parseFloat(s[3]);
					
					normals.add(new VectorF(x, y, z));
					
				} break;
				case "tex": break;
				case "f":
				{
					if (s.length != 4)
					{
						Logger.warn("OBJ mesh %s isn't using triangular faces!", asset);
						
					}
					
					int[] inds = new int[3];
					
					for (int c = 0; c < 3; c++)
					{
						String f = s[c + 1];
						
						String[] ind = f.split("/");
						
						if (ind.length == 0)
						{
							continue;
						}
						
						VectorF tex = null;
						VectorF nrm = null;
						
						if (ind.length > 1)
						{
							if (ind.length > 2)
							{
								nrm = normals.get(Integer.parseInt(ind[2]) - 1);
								
							}
							
							String t = ind[1];
							
							if (!t.equals(""))
							{
								tex = texCoords.get(Integer.parseInt(t) - 1);
								
							}
							
						}
						
						ModelPoint p = new ModelPoint(positions.get(Integer.parseInt(ind[0]) - 1), tex, nrm);
						
						int i = points.indexOf(p);
						
						if (i == -1)
						{
							i = points.size();
							
							points.add(p);
							
						}
						
						inds[c] = i;
						
					}
					
					for (int ind : inds)
					{
						indices.add(ind);
						
					}
					
				} break;
			
			}
			
		}
		
		if (positions.size() == 0)
		{
			throw new CaelumException("OBJ file provided was empty");
		}
		
		if (texCoords.size() != 0 && texCoords.size() != positions.size())
		{
			throw new CaelumException("OBJ file had illegal number of texture coordinates: %s", texCoords.size());
		}
		
		if (normals.size() != 0 && normals.size() != positions.size())
		{
			throw new CaelumException("OBJ file had illegal number of normals: %s", normals.size());
		}
		
		//TODO Optimize for triangle stripping
		
		FloatBuffer vtx = BufferHelper.createFloatBuffer((positions.size() + normals.size()) * 3 + (texCoords.size() * 2));
		
		for (int c = 0; c < positions.size(); c++)
		{
			VectorF pos = positions.get(c);
			
			vtx.put(pos.get(0));
			vtx.put(pos.get(1));
			vtx.put(pos.get(2));
			
			if (!texCoords.isEmpty())
			{
				VectorF tex = texCoords.get(c);
				
				vtx.put(tex.get(0));
				vtx.put(tex.get(1));
				
			}
			
			if (!normals.isEmpty())
			{
				VectorF n = normals.get(c);
				
				vtx.put(n.get(0));
				vtx.put(n.get(1));
				vtx.put(n.get(2));
				
			}
			
		}
		
		return new MeshData(vtx, BufferHelper.makeIntBuffer(indices), null, false, false, !texCoords.isEmpty(), !normals.isEmpty());
	}
	
}
