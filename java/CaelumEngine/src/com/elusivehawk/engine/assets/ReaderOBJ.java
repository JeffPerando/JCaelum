
package com.elusivehawk.engine.assets;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import com.elusivehawk.util.StringHelper;
import com.elusivehawk.util.math.Vector;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ReaderOBJ
{
	public Asset readAsset(AssetManager mgr, File file) throws Throwable
	{
		List<Vector> vertPositions = Lists.newArrayList(),
				vertTexCoords = Lists.newArrayList(),
				vertNormals = Lists.newArrayList();
		String texstring = "";
		int currentIndex = 0;
		List<Vector> vertices = new CopyOnWriteArrayList<Vector>();
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
				
				vertPositions.add(new Vector(x, y, z));
				
			}
			else if ("vt".equalsIgnoreCase(prefix))
			{
				float u = Float.parseFloat(s[1]);
				float v = Float.parseFloat(s[2]);
				
				vertTexCoords.add(new Vector(u, v));
				
			}
			else if ("vn".equalsIgnoreCase(prefix))
			{
				float x = Float.parseFloat(s[1]);
				float y = Float.parseFloat(s[2]);
				float z = Float.parseFloat(s[3]);
				
				vertNormals.add(new Vector(x, y, z));
				
			}
			else if ("tex".equalsIgnoreCase(prefix))
			{
				texstring = s[1];
				
			}
			else if ("f".equalsIgnoreCase(prefix))
			{
				/*for (int i = 1; i < 4; i++)
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
					
					for (Vector vO : vertices)
					{
						if (vert.equals(vO))
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
				*/
			}
			
		}
		
		//int texId = TextureLoader.getTextureIndex(texstring);
		
		//CaelumEngine.log().log(EnumLogType.INFO, String.format("Mesh loaded with %s vertices, with texture %s", arg1));
		//Mesh mesh = new Mesh(file.getName(), vertices, indices, texId);
		
		return null;
	}
	
}
