
import com.elusivehawk.engine.render.GL;
import com.elusivehawk.engine.render.Model;

/**
 * 
 * Makes a blue square.
 * 
 * @author Elusivehawk
 */
public class ModelProofOfConcept extends Model
{
	@Override
	public void loadData(Object... objs)
	{
		this.globalColor(0x00, 0x00, 0xFF, 0x00);
		
		this.begin(GL.GL_TRIANGLES);
		
		this.vertex(0.2f, 0.2f, 0);
		this.vertex(0.8f, 0.2f, 0);
		this.vertex(0.2f, 0.8f, 0);
		
		this.vertex(0.8f, 0.8f, 0);
		this.vertex(0.2f, 0.8f, 0);
		this.vertex(0.8f, 0.2f, 0);
		
		this.end();
		
	}
	
}
