
package elusivehawk.engine.render;

import java.util.HashSet;
import java.util.Set;
import elusivehawk.engine.math.Vector4f;

/**
 * 
 * Okay, so it's more like a "meat section", sue me.
 * 
 * @author Elusivehawk
 */
public class Bone
{
	public final int start, end;
	protected final Model parent;
	protected final Set<Bone> children = new HashSet<Bone>();
	
	public Bone(Model m, int stride, int count)
	{
		parent = m;
		start = stride * 4;
		end = start + count;
		
	}
	
	public void addChild(Bone b)
	{
		if (b.parent == this.parent)
		{
			this.children.add(b);
			
		}
		
	}
	
	public void rotate(Vector4f vec)
	{
		//this.parent.rotate(vec, this);
		
		if (!this.children.isEmpty())
		{
			for (Bone child : this.children)
			{
				child.rotate(vec);
				
			}
			
		}
		
	}
	
	public void scale(Vector4f vec)
	{
		//this.parent.scale(vec, this);
		
		if (!this.children.isEmpty())
		{
			for (Bone child : this.children)
			{
				child.scale(vec);
				
			}
			
		}
		
	}
	
	public void trans(Vector4f vec)
	{
		//this.parent.trans(vec, this);
		
		if (!this.children.isEmpty())
		{
			for (Bone child : this.children)
			{
				child.trans(vec);
				
			}
			
		}
		
	}
	
}
