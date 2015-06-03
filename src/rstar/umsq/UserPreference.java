package rstar.umsq;

import util.Constants;

/**
 * 
 * @author niexiao
 *
 */
public class UserPreference {
	private float[] weights;
	
	public UserPreference() {
		// TODO Auto-generated constructor stub
	}
	
	public  UserPreference(float[] weights) {
		this.weights = weights;
		self_check();
	}
	
	
	private void self_check() {
		if (weights.length != Constants.ATTR_DIMENSION + 1) {
			throw new IllegalArgumentException("User preference not match non-spatial values");
		}
		float total_weight = 0;
		for (int i = 0; i < weights.length; i++) {
			total_weight += weights[i];
		}
		if(total_weight != 1.0) 
			throw new IllegalArgumentException("The sum of user weights is not 1");
	}
	
	public int getPreferenceSize() {
		return weights.length;
	}
	
	public float[] getAllPreference() {
		return weights;
	}
	
	public float getPreferenceByIndex(int index) {
		return weights[index];
	}
}
