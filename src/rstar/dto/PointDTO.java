package rstar.dto;

public class PointDTO extends AbstractDTO{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1150536737702784734L;
	public float oid;
    public float[] coords;
    public float[] non_spatials;
    public String digest;

    public PointDTO(float oid, float[] coords, float[] non_spatials, String digest) {
        this.oid = oid;
        this.coords = coords;
        this.non_spatials = non_spatials;
        this.digest = digest;
    }
}
