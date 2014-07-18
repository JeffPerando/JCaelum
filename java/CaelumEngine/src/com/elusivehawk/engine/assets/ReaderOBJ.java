
package com.elusivehawk.engine.assets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import com.elusivehawk.engine.core.CaelumEngine;
import com.elusivehawk.engine.core.EnumLogType;
import com.elusivehawk.util.StringHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ReaderOBJ implements IAssetReader
{
	@Override
	public Asset readAsset(AssetManager mgr, File file) throws Throwable
	{
		List<VertPosition> vertPositions = new ArrayList<VertPosition>();
		List<VertTexCoord> vertTexCoords = new ArrayList<VertTexCoord>();
		List<VertNormal> vertNormals = new ArrayList<VertNormal>();
		String texstring = "";
		int currentIndex = 0;
		List<Vertex> vertices = new CopyOnWriteArrayList<Vertex>();
		List<Integer> indices = new ArrayList<Integer>();
		
		List<String> txt = StringHelper.read(file);
		
		for (String line : txt)
		{
			if (line.startsWith("#"))
			{
				continue;
			}
			
			String[] s = line.split(" ");
			
			String prefix = s[0];
			
			if ("v".equalsIgnoreCase(prefix))
			{
				float x = Float.parseFloat(s[1]);
				float y = Float.parseFloat(s[2]);
				float z = Float.parseFloat(s[3]);
				
				vertPositions.add(new VertPosition(x, y, z));
				
			}
			else if ("vt".equalsIgnoreCase(prefix))
			{
				float u = Float.parseFloat(s[1]);
				float v = Float.parseFloat(s[2]);
				
				vertTexCoords.add(new VertTexCoord(u, v));
				
			}
			else if ("vn".equalsIgnoreCase(prefix))
			{
				float x = Float.parseFloat(s[1]);
				float y = Float.parseFloat(s[2]);
				float z = Float.parseFloat(s[3]);
				
				vertNormals.add(new VertNormal(x, y, z));
				
			}
			else if ("tex".equalsIgnoreCase(prefix)))
			{
				texstring = s[1];
				
			}
			else if ("f".equalsIgnoreCase(prefix))
			{
				for (int i = 1; i < 4; i++)
				{
					Vertex vert = new Vertex(new VertPosition(1, 1, 1));
					String[] faceIndices = s[i].split("/");
					
					if (!(faceIndices[0].equals("")) && !(faceIndices[0] == null))
					{
						vert.vertPosition = vertPositions.get(Integer.valueOf(faceIndices[0]) - 1);
					}
					
					if (!(faceIndices[1].equals("")) && !(faceIndices[0] == null))
					{
						vert.vertTexCoord = vertTexCoords.get(Integer.valueOf(faceIndices[1]) - 1);                                            
					}
					
					if (!(faceIndices[2].equals("")) && !(faceIndices[0] == null))
					{
						vert.vertNormal = vertNormals.get(Integer.valueOf(faceIndices[2]) - 1);
					}
					
					vert.setColour(new VertColour(1.0f, 1.0f, 1.0f));
					boolean foundSame = false;
					
					for (Vertex vO : vertices)
					{
						if (vert.isTheSameAs(vO))
						{
							foundSame = true;
							indices.add(vO.getIndex());
							continue;
						}
						
					}
					
					if (!foundSame)
					{
						vert.setIndex(currentIndex);
						vertices.add(vert);
						indices.add(vert.getIndex());
						currentIndex++;
						
					}
					
				}
				
			}
			
		}
		
		int texId = TextureLoader.getTextureIndex(texstring);
		
		CaelumEngine.log().log(EnumLogType.INFO, String.format("Mesh loaded with %s vertices, with texture %s", arg1));
		Debug.log(Debug.LogType.INFO, "Mesh loaded with " + vertices.size()
				+ " vertices, using texture ID " + texId + ".");
		Mesh mesh = new Mesh(file.getName(), vertices, indices, texId);
		
		return mesh;
	}
	
}
