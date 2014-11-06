
package com.elusivehawk.caelum.prefab;

import com.elusivehawk.caelum.assets.Asset;
import com.elusivehawk.caelum.render.Mesh;
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
	
	public MeshComponent(Component parent, Mesh mesh)
	{
		this(parent, new MeshRenderer(mesh));
		
	}
	
	public MeshComponent(Component parent, MeshRenderer mr)
	{
		this(parent, 0, mr);
		
	}
	
	public MeshComponent(Component parent, int p, Mesh mesh, IPopulator<Component> pop)
	{
		this(parent, p, new MeshRenderer(mesh), pop);
		
	}
	
	public MeshComponent(Component parent, int p, MeshRenderer mr, IPopulator<Component> pop)
	{
		this(parent, p, mr);
		
		pop.populate(this);
		
	}
	
	public MeshComponent(Component parent, int p, Mesh mesh)
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
	public void preRender(RenderContext rcon, double delta)
	{
		this.meshRenderer.preRender(rcon, delta);
		
		super.preRender(rcon, delta);
		
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
	
	@Override
	public void onAssetLoaded(Asset a)
	{
		this.meshRenderer.onAssetLoaded(a);
		
		super.onAssetLoaded(a);
		
	}
	
	public MeshRenderer getMeshRenderer()
	{
		return this.meshRenderer;
	}
	
}
