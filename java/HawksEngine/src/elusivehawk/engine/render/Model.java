
package elusivehawk.engine.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import elusivehawk.engine.math.Vector3f;
import elusivehawk.engine.util.BufferHelper;
import elusivehawk.engine.util.GameLog;
import elusivehawk.engine.util.Tuple;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Model
{
	public final FloatBuffer fin;
	public final IntBuffer indices;
	
	public final int polyCount;
	public final VertexBufferObject finBuf, indiceBuf;
	
	private List<Vector3f> polys = new ArrayList<Vector3f>();
	private List<Color> color = new ArrayList<Color>();
	private List<Tuple<Float, Float>> texOffs = new ArrayList<Tuple<Float, Float>>();
	private int glMode = Integer.MIN_VALUE;
	private int pointCount = 0, oldPointCount = 0;
	private Color globalColor = null;
	private HashMap<Integer, Tuple<Integer, Integer>> arrays = new HashMap<Integer, Tuple<Integer, Integer>>();
	
	public Model()
	{
		loadData();
		
		if (polys.size() == 0)
		{
			throw new RuntimeException("You forgot to load polygons!");
			
		}
		
		if (glMode != Integer.MIN_VALUE)
		{
			throw new RuntimeException("You forgot to call end()!");
			
		}
		
		List<Vector3f> vecs = new ArrayList<Vector3f>();
		List<Integer> indiceList = new ArrayList<Integer>();
		List<Float> temp = new ArrayList<Float>();
		
		for (int c = 0; c < polys.size(); c++)
		{
			Vector3f vec = polys.get(c);
			
			int index = vecs.indexOf(vec);
			
			if (index == -1)
			{
				Color col = color.get(c);
				Tuple<Float, Float> tex = texOffs.get(c);
				
				vecs.add(vec);
				index = vecs.size() - 1;
				
				temp.add(vec.x);
				temp.add(vec.y);
				temp.add(vec.z);
				
				if (globalColor != null)
				{
					FloatBuffer mixed = RenderHelper.mixColors(col, globalColor);
					
					while (mixed.remaining() != 0)
					{
						temp.add(mixed.get());
						
					}
					
				}
				else
				{
					temp.add(col.getColorFloat(EnumColorFilter.RED));
					temp.add(col.getColorFloat(EnumColorFilter.GREEN));
					temp.add(col.getColorFloat(EnumColorFilter.BLUE));
					temp.add(col.getColorFloat(EnumColorFilter.ALPHA));
					
				}
				
				temp.add(tex.one);
				temp.add(tex.two);
				
			}
			
			indiceList.add(index);
			
		}
		
		fin = BufferHelper.makeFloatBuffer(temp).asReadOnlyBuffer();
		indices = BufferHelper.makeIntBuffer(indiceList).asReadOnlyBuffer();
		
		int vb = GL.glGetInteger(GL.GL_VERTEX_ARRAY);
		
		if (vb != 0)
		{
			GameLog.warn("Temporarily unbinding vertex array!");
			GL.glBindVertexArray(0);
			
		}
		
		IntBuffer vbos = RenderHelper.createVBOs(2);
		
		finBuf = new VertexBufferObject(vbos, GL.GL_ARRAY_BUFFER);
		finBuf.loadData(fin, GL.GL_STATIC_DRAW);
		
		indiceBuf = new VertexBufferObject(vbos, GL.GL_ELEMENT_ARRAY_BUFFER);
		indiceBuf.loadData(indices, GL.GL_STATIC_DRAW);
		
		if (vb != 0)
		{
			GameLog.warn("Rebinding vertex array");
			GL.glBindVertexArray(vb);
			
		}
		
		polys = null;
		color = null;
		texOffs = null;
		
		polyCount = pointCount;
		
	}
	
	public abstract void loadData();
	
	protected final void begin(int gl)
	{
		if (this.glMode != Integer.MIN_VALUE)
		{
			throw new RuntimeException("You're already adding vertices!");
			
		}
		
		if (RenderHelper.getPointCount(gl) == 0)
		{
			throw new RuntimeException("Invalid GL mode!");
			
		}
		
		if (this.arrays.containsKey(gl))
		{
			throw new RuntimeException("You already used this mode!");
			
		}
		
		this.glMode = gl;
		this.oldPointCount = this.polys.size();
		
	}
	
	protected final void end()
	{
		if (this.glMode == Integer.MIN_VALUE)
		{
			throw new RuntimeException("You need to call begin() first!");
			
		}
		
		int points = RenderHelper.getPointCount(this.glMode);
		int vectors = (this.polys.size() - this.oldPointCount);
		
		if (vectors % points != 0)
		{
			throw new RuntimeException("Odd number of vectors loaded: " + vectors + " in mode " + this.glMode);
			
		}
		
		this.pointCount += vectors;
		
		while (this.color.size() < this.polys.size())
		{
			this.color.add(new Color(EnumColorFormat.RGBA));
			
		}
		
		while (this.texOffs.size() < this.polys.size())
		{
			this.texOffs.add(new Tuple<Float, Float>(0f, 0f));
			
		}
		
		Tuple<Integer, Integer> t = new Tuple<Integer, Integer>(this.oldPointCount, this.pointCount);
		
		this.arrays.put(this.glMode, t);
		
		this.glMode = Integer.MIN_VALUE;
		
	}
	
	protected final void vertex(float x, float y, float z)
	{
		this.polys.add(new Vector3f(x, y, z));
		
	}
	
	protected final void color(int r, int g, int b, int a)
	{
		this.color(new Color(EnumColorFormat.RGBA, r, g, b, a));
		
	}
	
	protected final void color(Color col)
	{
		if (this.globalColor != null)
		{
			GameLog.warn("Global color has already been set. Silly programmer...");
			
			return;
		}
		
		this.color.add(col);
		
	}
	
	protected final void globalColor(int r, int g, int b, int a)
	{
		this.globalColor(new Color(EnumColorFormat.RGBA, r, g, b, a));
		
	}
	
	protected final void globalColor(Color col)
	{
		this.globalColor = col;
		
	}
	
	protected final void texOff(float x, float y)
	{
		this.texOffs.add(new Tuple<Float, Float>(x, y));
		
	}
	
	public HashMap<Integer, Tuple<Integer, Integer>> getArrays()
	{
		return this.arrays;
	}
	
}
