
package com.elusivehawk.caelum.prefab;

import com.elusivehawk.caelum.render.IMesh;
import com.elusivehawk.caelum.render.MeshRenderer;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.math.Quaternion;
import com.elusivehawk.util.math.Vector;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class MeshComponent extends PositionedComponent
{
	private final MeshRenderer meshRenderer;
	
	public MeshComponent(Component parent, IMesh mesh)
	{
		this(parent, new MeshRenderer(mesh));
		
	}
	
	public MeshComponent(Component parent, MeshRenderer mr)
	{
		this(parent, 0, mr);
		
	}
	
	public MeshComponent(Component parent, int p, IMesh mesh, IPopulator<Component> pop)
	{
		this(parent, p, new MeshRenderer(mesh), pop);
		
	}
	
	public MeshComponent(Component parent, int p, MeshRenderer mr, IPopulator<Component> pop)
	{
		this(parent, p, mr);
		
		pop.populate(this);
		
	}
	
	public MeshComponent(Component parent, int p, IMesh mesh)
	{
		this(parent, p, new MeshRenderer(mesh));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public MeshComponent(Component parent, int p, MeshRenderer mr)
	{
		super(parent, p);
		
		assert mr != null;
		
		meshRenderer = mr;
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		this.meshRenderer.preRender(rcon);
		
		super.preRender(rcon);
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		this.meshRenderer.render(rcon);
		
		super.render(rcon);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.meshRenderer.postRender(rcon);
		
		super.postRender(rcon);
		
	}
	
	@Override
	public void onVecChanged(Vector vec)
	{
		super.onVecChanged(vec);
		
		this.meshRenderer.onVecChanged(vec);
		
	}
	
	@Override
	public void onQuatChanged(Quaternion q)
	{
		super.onQuatChanged(q);
		
		this.meshRenderer.onQuatChanged(q);
		
	}
	
	public MeshRenderer getMeshRenderer()
	{
		return this.meshRenderer;
	}
	
}
