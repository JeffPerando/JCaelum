
package com.elusivehawk.caelum.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.render.gl.GLBuffer;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLEnumBufferTarget;
import com.elusivehawk.caelum.render.gl.GLEnumDataUsage;
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
public class MeshData implements IDeletable
{
	public final FloatBuffer vertex;
	public final IntBuffer indices;
	public final VectorF size;
	public final boolean isTriStrip, useTex, useNorm;
	public final int texSize;
	
	private GLBuffer vbo = null, glind = null;
	private boolean loaded = false;
	
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
	
	@Override
	public void delete()
	{
		if (this.loaded)
		{
			this.vbo.delete();
			this.glind.delete();
			
		}
		
	}
	
	public GLBuffer getVBO()
	{
		return this.vbo;
	}
	
	public GLBuffer getIBO()
	{
		return this.glind;
	}
	
	public boolean isLoaded()
	{
		return this.loaded;
	}
	
	public void load(RenderContext rcon)
	{
		if (!this.loaded)
		{
			this.vbo = new GLBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER, this.vertex, GLEnumDataUsage.GL_STATIC_DRAW, rcon, ((buf) ->
			{
				int stride = 12 + (this.useTex ? this.texSize * 4 : 0) + (this.useNorm ? 12 : 0);
				
				buf.addAttrib(RenderConst.VERTEX, 12, GLConst.GL_FLOAT, stride, 0);
				
				if (this.useTex)
				{
					buf.addAttrib(RenderConst.TEXCOORD, this.texSize * 4, GLConst.GL_FLOAT, stride, 0);
					
				}
				
				if (this.useNorm)
				{
					buf.addAttrib(RenderConst.NORMAL, 12, GLConst.GL_FLOAT, stride, 0);
					
				}
				
			}));
			
			this.glind = new GLBuffer(GLEnumBufferTarget.GL_ELEMENT_ARRAY_BUFFER, this.indices, GLEnumDataUsage.GL_STATIC_DRAW, rcon);
			
			Deletables.instance().register(this);
			
			this.loaded = true;
			
		}
		
	}
	
	public static MeshData fromJson(JsonObject json)
	{
		boolean strip = json.getBool("strip");
		boolean tex3d = json.getBool("3dTex");
		
		int texSize = tex3d ? 3 : 2;
		
		JsonArray vtxJson = json.getValue("vertices", JsonArray.class);
		
		if (vtxJson.length() % 3 != 0)
		{
			throw new CaelumException("The length of JSON array \"vertices\" is not divisible by 3");
		}
		
		int length = vtxJson.length();
		
		JsonArray texJson = json.getValue("texOffs", JsonArray.class);
		
		if (texJson != null)
		{
			if (texJson.length() % texSize != 0)
			{
				throw new CaelumException("The length of JSON array \"texOffs\" is not divisible by %s", texSize);
			}
			
			if (texJson.length() / texSize != vtxJson.length() / 3)
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
			vtx.put(vtxJson.getFloat(vtxI++));
			vtx.put(vtxJson.getFloat(vtxI++));
			vtx.put(vtxJson.getFloat(vtxI++));
			
			if (texJson != null)
			{
				vtx.put(texJson.getFloat(texI++));
				vtx.put(texJson.getFloat(texI++));
				
				if (tex3d)
				{
					vtx.put(texJson.getFloat(texI++));
					
				}
				
			}
			
			if (normJson != null)
			{
				vtx.put(normJson.getFloat(normI++));
				vtx.put(normJson.getFloat(normI++));
				vtx.put(normJson.getFloat(normI++));
				
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
				indices.put(indJson.getInt(c));
				
			}
			
		}
		
		return new MeshData(vtx, indices, size, strip, tex3d, texJson != null, normJson != null);
	}
	
}
