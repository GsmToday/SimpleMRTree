package rstar.dto;

public class MbrDTO extends AbstractDTO{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -3059436475125528892L;
	public float[][] points;
	public float[] opt_attrs;
	public int attr_dimensions;
    
    public MbrDTO(float[][] points, float[] opt_attrs) {
        this.points = points;
        this.opt_attrs = opt_attrs;
        attr_dimensions = opt_attrs.length;
    }
}
