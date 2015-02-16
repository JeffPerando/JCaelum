
package com.elusivehawk.caelum.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.util.MakeStruct;
import com.elusivehawk.util.math.VectorF;
import com.elusivehawk.util.parse.json.JsonArray;
import com.elusivehawk.util.parse.json.JsonObject;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@MakeStruct
public class MeshData
{
	public final FloatBuffer vertex;
	public final IntBuffer indices;
	public final VectorF size;
	public final boolean isTriStrip, useTex, useNorm;
	public final int texSize;
	
	@SuppressWarnings("unqualified-field-access")
	public MeshData(FloatBuffer vtx, IntBuffer ind, VectorF vec, boolean strip, boolean tex3d, boolean tex, boolean norm)
	{
		assert vtx != null;
		
		vertex = vtx.asReadOnlyBuffer();
		indices = ind == null ? null : ind.asReadOnlyBuffer();
		size = (VectorF)vec.clone().setImmutable();
		isTriStrip = strip;
		texSize = tex3d ? 3 : 2;
		useTex = tex;
		useNorm = norm;
		
	}
	
	public static MeshData fromJson(JsonObject json)
	{
		boolean strip = json.getValue("strip", Boolean.class, false);
		boolean vtx3d = json.getValue("3dMesh", Boolean.class, true);
		boolean tex3d = json.getValue("3dTex", Boolean.class, false);
		
		int vtxSize = vtx3d ? 3 : 2;
		int texSize = tex3d ? 3 : 2;
		
		JsonArray vtxJson = json.getValue("vertices", JsonArray.class);
		
		if (vtxJson.length() % vtxSize != 0)
		{
			throw new CaelumException("The length of JSON array \"vertices\" is not divisible by %s", vtxSize);
		}
		
		int length = vtxJson.length();
		
		JsonArray texJson = json.getValue("texOffs", JsonArray.class);
		
		if (texJson != null)
		{
			if (texJson.length() % texSize != 0)
			{
				throw new CaelumException("The length of JSON array \"texOffs\" is not divisible by %s", texSize);
			}
			
			if (texJson.length() / texSize != vtxJson.length() / vtxSize)
			{
				throw new CaelumException("The length of JSON array \"texOffs\" contains a different number of indices than \"vertices\"");
			}
			
			length += texJson.length();
			
		}
		
		JsonArray normJson = json.getValue("normals", JsonArray.class);
		
		if (normJson != null)
		{
			if (texJson.length() != vtxJson.length())
			{
				throw new CaelumException("The length of JSON array \"normals\" is different than the length of \"vertices\"");
			}
			
			length += normJson.length();
			
		}
		
		FloatBuffer vtx = BufferHelper.createFloatBuffer(length);
		
		int vtxI = 0;
		int texI = 0;
		int normI = 0;
		
		for (int c = 0; c < vtxJson.length(); c += 3)
		{
			vtx.put(vtxJson.getValue(vtxI++, Number.class).floatValue());
			vtx.put(vtxJson.getValue(vtxI++, Number.class).floatValue());
			vtx.put(vtx3d ? vtxJson.getValue(vtxI++, Number.class).floatValue() : 0f);
			
			if (texJson != null)
			{
				vtx.put(texJson.getValue(texI++, Number.class).floatValue());
				vtx.put(texJson.getValue(texI++, Number.class).floatValue());
				
				if (tex3d)
				{
					vtx.put(texJson.getValue(texI++, Number.class).floatValue());
					
				}
				
			}
			
			if (normJson != null)
			{
				vtx.put(normJson.getValue(normI++, Number.class).floatValue());
				vtx.put(normJson.getValue(normI++, Number.class).floatValue());
				vtx.put(normJson.getValue(normI++, Number.class).floatValue());
				
			}
			
		}
		
		VectorF min = new VectorF(3);
		VectorF max = new VectorF(3);
		
		for (int c = 0; c < vtx.capacity(); c += 3)
		{
			for (int i = 0; i < 3; i++)
			{
				min.set(i, Math.min(min.get(c), vtx.get(c + i)));
				max.set(i, Math.max(min.get(c), vtx.get(c + i)));
				
			}
			
		}
		
		VectorF size = (VectorF)min.absolute().add(max);
		
		IntBuffer indices = null;
		
		JsonArray indJson = json.getValue("indices", JsonArray.class);
		
		if (indJson != null)
		{
			indices = BufferHelper.createIntBuffer(indJson.length());
			
			for (int c = 0; c < indJson.length(); c++)
			{
				indices.put(indJson.getValue(c, Long.class, 0L).intValue());
				
			}
			
		}
		
		return new MeshData(vtx, indices, size, strip, tex3d, texJson != null, normJson != null);
	}
	
}
