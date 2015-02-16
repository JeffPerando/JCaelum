
package com.elusivehawk.caelum.prefab;

import com.elusivehawk.caelum.render.IMesh;
import com.elusivehawk.caelum.render.MeshRenderer;
import com.elusivehawk.caelum.render.RenderContext;
import com.elusivehawk.caelum.render.RenderException;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.math.QuaternionF;
import com.elusivehawk.util.math.VectorF;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class MeshComponent extends PositionedComponent
{
	private final MeshRenderer meshRenderer;
	
	public MeshComponent(IMesh mesh)
	{
		this(new MeshRenderer(mesh));
		
	}
	
	public MeshComponent(IMesh mesh, IPopulator<Component> pop)
	{
		this(mesh);
		
		pop.populate(this);
		
	}
	
	public MeshComponent(MeshRenderer mr)
	{
		this(0, mr);
		
	}
	
	public MeshComponent(MeshRenderer mr, IPopulator<Component> pop)
	{
		this(mr);
		
		pop.populate(this);
		
	}
	public MeshComponent(int p, IMesh mesh)
	{
		this(p, new MeshRenderer(mesh));
		
	}
	
	public MeshComponent(int p, IMesh mesh, IPopulator<Component> pop)
	{
		this(p, mesh);
		
		pop.populate(this);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public MeshComponent(int p, MeshRenderer mr)
	{
		super(p);
		
		assert mr != null;
		
		meshRenderer = mr;
		
	}
	
	public MeshComponent(int p, MeshRenderer mr, IPopulator<Component> pop)
	{
		this(p, mr);
		
		pop.populate(this);
		
	}
	
	@Override
	public void preRender(RenderContext rcon)
	{
		this.meshRenderer.preRender(rcon);
		
		super.preRender(rcon);
		
	}
	
	@Override
	public void postRender(RenderContext rcon)
	{
		this.meshRenderer.postRender(rcon);
		
		super.postRender(rcon);
		
	}
	
	@Override
	public void delete(RenderContext rcon)
	{
		this.meshRenderer.delete(rcon);
		
		super.delete(rcon);
		
	}
	
	@Override
	public void render(RenderContext rcon) throws RenderException
	{
		this.meshRenderer.render(rcon);
		
		super.render(rcon);
		
	}
	
	@Override
	public void onVecChanged(VectorF vec)
	{
		super.onVecChanged(vec);
		
		this.meshRenderer.onVecChanged(vec);
		
	}
	
	@Override
	public void onQuatChanged(QuaternionF q)
	{
		super.onQuatChanged(q);
		
		this.meshRenderer.onQuatChanged(q);
		
	}
	
	public MeshRenderer getMeshRenderer()
	{
		return this.meshRenderer;
	}
	
}
