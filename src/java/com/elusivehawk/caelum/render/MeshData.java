
package com.elusivehawk.caelum.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.elusivehawk.caelum.CaelumException;
import com.elusivehawk.caelum.render.gl.GLBuffer;
import com.elusivehawk.caelum.render.gl.GLConst;
import com.elusivehawk.caelum.render.gl.GLEnumBufferTarget;
import com.elusivehawk.caelum.render.gl.GLEnumDataUsage;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.parse.json.JsonArray;
import com.elusivehawk.util.parse.json.JsonObject;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class MeshData implements IDeletable
{
	private final FloatBuffer vertex;
	private IntBuffer indices = null;
	private boolean triStrip = false, useNorm = false;
	private int texSize = 0;
	
	private GLBuffer vbo = null, glind = null;
	private boolean locked = false, loaded = false;
	
	@SuppressWarnings("unqualified-field-access")
	public MeshData(FloatBuffer vtx)
	{
		assert vtx != null;
		
		vertex = vtx.asReadOnlyBuffer();
		
	}
	
	public MeshData(FloatBuffer vtx, IPopulator<MeshData> pop)
	{
		this(vtx);
		
		pop.populate(this);
		
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
	
	public MeshData indices(IntBuffer ind)
	{
		if (this.locked)
		{
			throw new RenderException("Locked mesh!");
		}
		
		assert ind != null;
		
		this.indices = ind.asReadOnlyBuffer();
		
		return this;
	}
	
	public MeshData strip()
	{
		if (this.locked)
		{
			throw new RenderException("Locked mesh!");
		}
		
		this.triStrip = true;
		
		return this;
	}
	
	public MeshData useNormals()
	{
		if (this.locked)
		{
			throw new RenderException("Locked mesh!");
		}
		
		this.useNorm = true;
		
		return this;
	}
	
	public MeshData texCoordSize(int tsize)
	{
		if (this.locked)
		{
			throw new RenderException("Locked mesh!");
		}
		
		assert tsize > 0;
		
		this.texSize = tsize;
		
		return this;
	}
	
	public MeshData lock()
	{
		if (!this.locked)
		{
			this.locked = true;
			
		}
		
		return this;
	}
	
	public GLBuffer getVBO()
	{
		return this.vbo;
	}
	
	public GLBuffer getIBO()
	{
		return this.glind;
	}
	
	public FloatBuffer vertex()
	{
		return this.vertex;
	}
	
	public IntBuffer indices()
	{
		return this.indices;
	}
	
	public int texSize()
	{
		return this.texSize;
	}
	
	public boolean isStrip()
	{
		return this.triStrip;
	}
	
	public boolean hasNormals()
	{
		return this.useNorm;
	}
	
	public boolean isLocked()
	{
		return this.locked;
	}
	
	public boolean isLoaded()
	{
		return this.loaded;
	}
	
	public void load(RenderContext rcon)
	{
		if (!this.locked)
		{
			throw new RenderException("Not locked!");
		}
		
		if (!this.loaded)
		{
			this.vbo = new GLBuffer(GLEnumBufferTarget.GL_ARRAY_BUFFER, this.vertex, GLEnumDataUsage.GL_STATIC_DRAW, rcon, ((buf) ->
			{
				int stride = 12 + (this.texSize > 0 ? this.texSize * 4 : 0) + (this.useNorm ? 12 : 0);
				
				buf.addAttrib(RenderConst.VERTEX, 12, GLConst.GL_FLOAT, stride, 0);
				
				if (this.texSize > 0)
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
		
		int texSize = json.getBool("3dTex") ? 3 : 2;
		
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
				for (int t = 0; t < texSize; t++)
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
		
		MeshData ret = new MeshData(vtx);
		
		ret.texCoordSize(texSize);
		
		if (normJson != null)
		{
			ret.useNormals();
			
		}
		
		JsonArray indJson = json.getValue("indices", JsonArray.class);
		
		if (indJson != null)
		{
			ret.indices(indJson.toIntBuffer());
			
		}
		
		if (strip)
		{
			ret.strip();
			
		}
		
		ret.lock();
		
		return ret;
	}
	
}
