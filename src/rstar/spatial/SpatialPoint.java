package rstar.spatial;

import rstar.dto.PointDTO;
import rstar.interfaces.IDtoConvertible;
import util.DigestUtil;

public class SpatialPoint implements IDtoConvertible {
    private int _dimension;
    private float[] _cords;
    private int _attr_dimension;
    private float[] _non_spatial;
    private float  _oid;
    private String _digest;

    public SpatialPoint() {
    }

    public SpatialPoint(int dimension, int attr_dimension) {
        this._dimension = dimension;
        this._attr_dimension = attr_dimension;
        this._oid = -1;
    }

    public SpatialPoint(float[] cords, float[] non_spatial) {
        this._cords = cords;
        this._non_spatial = non_spatial;
        this._dimension = cords.length;
        this._attr_dimension = non_spatial.length;
        this._digest = computeDigest();
        this._oid = -1;
    }

    public SpatialPoint(float[] cords, float[] non_spatial, float oid) {
        this._cords = cords;
        this._dimension = cords.length;
        this._non_spatial = non_spatial;
        this._attr_dimension = non_spatial.length;
        this._digest = computeDigest();
        this._oid = oid;
    }

    public SpatialPoint(PointDTO dto) {
        this._cords = dto.coords;
        this._non_spatial = dto.non_spatials;
        this._dimension = dto.coords.length;
        this._attr_dimension = dto.non_spatials.length;
        this._digest = dto.digest;
        this._oid = dto.oid;
    }

    private String computeDigest() {
        return DigestUtil.digest(this.toString(), "sha-1");
    }

    public int getDimension(){
        return _dimension;
    }

    public int getAttrDimension() {
        return _attr_dimension;
    }

    public void setCords(float[] data){
        this._cords = data;
    }

    public float[] getCords() {
        return _cords;
    }

    public void setAttrs(float[] non_spatial) {
        this._non_spatial = non_spatial;
    }

    public float[] getAttrs() {
        return this._non_spatial;
    }

    public float getOid() {
        return _oid;
    }

    public void setOid(float oid) {
        this._oid = oid;
    }

    /**
     * calculate distance of this point with <pre>otherPoint</pre>
     * @param otherPoint the point from which this point's
     *                   distance is to be calculated
     * @return distance from <pre>otherPoint</pre>
     */
    public float distance(SpatialPoint otherPoint) {
        float[] otherPoints = otherPoint.getCords();
        float distance = 0;
        for (int i = 0; i < _cords.length; i++) {
            float tmp = (_cords[i] * _cords[i]) - (otherPoints[i] * otherPoints[i]);
            if(tmp < 0)
                tmp = -1 * tmp;

            distance += tmp;
        }
        return (float)Math.pow(distance, 0.5);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        for (double cord : _cords) {
            str.append(cord).append(",");
        }
        for(float attr : _non_spatial) {
            str.append(attr).append(",");
        }
        str.append("]");
        return str.toString();
    }

    @SuppressWarnings("unchecked")
	@Override
    public PointDTO toDTO() {
        return new PointDTO(_oid, _cords, _non_spatial, _digest);
    }
}
