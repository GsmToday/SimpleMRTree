package rstar.umsq;

public class SingleQuery {
	private float[] cords;
	private UserPreference preference;
	private int userid;
	
	public SingleQuery() {
		// TODO Auto-generated constructor stub
	}
	
	public SingleQuery(float[] cords, UserPreference preference) {
		this.cords = cords;
		this.preference = preference;
	}
	
	public SingleQuery(int userid, float[] cords, UserPreference preference) {
		this(cords, preference);
		this.userid = userid;
	}
	
	public float[] getUserLocation() {
		return cords;
	}
	
	public float getUserPreferOnDistance() {
		return preference.getPreferenceByIndex(0);
	}
	
	public float[] getUserPreferOnAttrs() {
		float[] attrsPrefer = new float[preference.getPreferenceSize()-1];
		for (int i = 0; i < attrsPrefer.length; i++) {
			attrsPrefer[i] = preference.getPreferenceByIndex(i+1);
		}
		
		return attrsPrefer;
	}
	
	public int getUserID() {
		return userid;
	}
}
