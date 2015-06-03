package rstar.spatial;

import rstar.nodes.RStarNode;
import rstar.dto.MbrDTO;
import rstar.interfaces.IDtoConvertible;
import util.Constants;
import java.util.List;

public class HyperRectangle implements IDtoConvertible {
    private int _dimension;
    /**
     * points is a 2D double array containing
     * the max and min values for each dimension
     * in the rectangle.
     */
    private float[][] points;
    private float[] opt_attrs;
    public static final int MAX_CORD = 0;
    public static final int MIN_CORD = 1;

    public float[][] getPoints() {
        return points;
    }

    public float[] getOptAttrs() {
		return opt_attrs;
	}
    
    public void setPoints(float[][] points) {
        this.points = points;
    }

    public void setOptAttrs(float[] opt_attrs) {
    	this.opt_attrs = opt_attrs;
    }
    
    public HyperRectangle(int dimension) {
        this._dimension = dimension;
        points = new float[dimension][2];
        opt_attrs = new float[Constants.ATTR_DIMENSION];
    }

    public HyperRectangle(int dimension, SpatialPoint[] points) {
        this._dimension = dimension;
        this.points = new float[dimension][2];
        opt_attrs = new float[Constants.ATTR_DIMENSION];
        update(points);
    }

    public <T> HyperRectangle(int dimension, List<T> points) {
        this._dimension = dimension;
        this.points = new float[dimension][2];
        opt_attrs = new float[Constants.ATTR_DIMENSION];
        update(points);
        /*if (points.get(0) instanceof RStarNode)
            update(List<RStarNode> points);
        else
            update((List<SpatialPoint>) points);*/
    }

    public HyperRectangle(MbrDTO dto) {
        this._dimension = Constants.DIMENSION;
        this.points = dto.points;
        this.opt_attrs = dto.opt_attrs;
    }

    public HyperRectangle(float[] cords, int[] attrs) {
        this._dimension = cords.length;
        points = new float[_dimension][2];
        for (int i = 0; i < _dimension; i++) {
            points[i][MAX_CORD] = cords[i];
            points[i][MIN_CORD] = cords[i];
        }
        
        for (int j = 0; j < Constants.ATTR_DIMENSION; j++) {
			opt_attrs[j] = attrs[j];
		}
    }
    
    public HyperRectangle(float[] cords) {
        this._dimension = cords.length;
        points = new float[_dimension][2];
        for (int i = 0; i < _dimension; i++) {
            points[i][MAX_CORD] = cords[i];
            points[i][MIN_CORD] = cords[i];
        }
    }

    public void update(SpatialPoint newPoint) {
        SpatialPoint[] newPoints = new SpatialPoint[1];
        newPoints[0] = newPoint;
        update(newPoints);
    }

    private void update(SpatialPoint[] newPoints) {
        for (SpatialPoint newPoint : newPoints) {
            float[] cord = newPoint.getCords();
            final float[] attrs = newPoint.getAttrs();
            assert cord.length == _dimension;

            for (int i = 0; i < cord.length; i++) {
                if (points[i][MAX_CORD] == 0 || points[i][MAX_CORD] < cord[i]) {
                    points[i][MAX_CORD] = cord[i];
                }
                if (points[i][MIN_CORD] == 0 || points[i][MIN_CORD] > cord[i]) {
                    points[i][MIN_CORD] = cord[i];
                }
            }
            //set the optimal attributes in the mbr
            for (int j = 0; j < attrs.length; j++) {
				opt_attrs[j] = Math.min(opt_attrs[j], attrs[j]);
			}        
        }
    }

    private <T> void update(List<T> newPoints) {
        if (newPoints.get(0) instanceof SpatialPoint) {
            for (T newPoint : newPoints) {
                float[] cord = ((SpatialPoint) newPoint).getCords();
                final float[] attrs = ((SpatialPoint) newPoint).getAttrs();
                assert cord.length == _dimension;
                for (int i = 0; i < cord.length; i++) {
                    if (points[i][MAX_CORD] == 0 || points[i][MAX_CORD] < cord[i]) {
                        points[i][MAX_CORD] = cord[i];
                    }
                    if (points[i][MIN_CORD] == 0 || points[i][MIN_CORD] > cord[i]) {
                        points[i][MIN_CORD] = cord[i];
                    }
                }
                
              //set the optimal attributes in the mbr
                for (int j = 0; j < attrs.length; j++) {
    				opt_attrs[j] = Math.min(opt_attrs[j], attrs[j]);
    			}   
            }
        } else if (newPoints.get(0) instanceof RStarNode) {
            for (T node : newPoints) {
            	update(((RStarNode) node).getMBR());
            }
        }
    }


//    private void update(List<RStarNode> nodes) {
//        for (RStarNode node : nodes) {
//            update(node.getMBR());
//        }
//    }

    public void update(HyperRectangle addedRegion) {
        float[][] newPoints = addedRegion.getPoints();
        final float[] attrs = addedRegion.getOptAttrs();
        assert newPoints.length == _dimension;
        
        for (int j = 0; j < _dimension; j++) {
            if (points[j][MAX_CORD] == 0 || points[j][MAX_CORD] < newPoints[j][MAX_CORD]) {
                points[j][MAX_CORD] = newPoints[j][MAX_CORD];
            }
            if (points[j][MIN_CORD] == 0 || points[j][MIN_CORD] > newPoints[j][MIN_CORD]) {
                points[j][MIN_CORD] = newPoints[j][MIN_CORD];
            }
        }
        for (int i = 0; i < Constants.ATTR_DIMENSION; i++) {
        	opt_attrs[i] = Math.min(opt_attrs[i], attrs[i]);
        }
    }

    /**
     * finds the intersecting region of this MBR with otherMBR
     * @param otherMBR the mbr with which this mbr's intersection
     *                 is to be calculated
     * @return the intersecting region, null if not intersecting
     */
    public HyperRectangle getIntersection(HyperRectangle otherMBR) {
        float[][] interPoints = new float[_dimension][2];
        float[][] newPoints = otherMBR.getPoints();
        assert newPoints.length == _dimension;

        boolean intersectExists = true;
        for (int i = 0; i < _dimension; i++) {
            if ((points[i][MAX_CORD] < newPoints[i][MIN_CORD]) || (points[i][MIN_CORD] > newPoints[i][MAX_CORD])) {
                intersectExists = false;
                break;
            }
            interPoints[i][MAX_CORD] = Math.min(newPoints[i][MAX_CORD], points[i][MAX_CORD]);
            interPoints[i][MIN_CORD] = Math.max(newPoints[i][MIN_CORD], points[i][MIN_CORD]);
        }
        	
        if (!intersectExists) {
            return null;
        }
        HyperRectangle intersect = new HyperRectangle(_dimension);
        intersect.setPoints(interPoints);
        
        //caution the returned intersect mbr does not initialize the attribute values;
        //this function must not be used in USMQ query.
        
        return intersect;
    }

    /**
     * finds the increment in volume of the newMbr is
     * added
     * @return 0 if no incement
     */
    public double deltaV_onInclusion(HyperRectangle newmbr) {
        HyperRectangle tempMbr = new HyperRectangle(_dimension);
        tempMbr.setPoints(points);
        tempMbr.update(newmbr);

        return tempMbr.volume() - this.volume();
    }

    /**
     * Computes the volume of this MBR.
     *
     * @return the volume of this MBR
     */
    public double volume() {
        double vol = 1;
        for (float[] point : points) {
            vol *= point[MAX_CORD] - point[MIN_CORD];
        }
        return vol;
    }

    /**
     * Computes the margin of this MBR.
     *
     * @return the margin of this MBR
     */
    public double margin() {
        double margin = 0;
        for (float[] point : points) {
            margin += point[MAX_CORD] - point[MIN_CORD];
        }
        return margin;
    }

    /**
     * Computes the volume of the overlapping box between this MBR and the given MBR
     * and return the relation between the volume of the overlapping box and the volume of both MBRs.
     *
     * @param mbr the MBR for which the intersection volume with this MBR should be computed
     * @return the relation between the volume of the overlapping box and the volume of this MBR
     *         and the given MBR
     */
    public double overlap(HyperRectangle mbr) {
        HyperRectangle intersect = this.getIntersection(mbr);
        if (intersect == null) {
            return 0;
        }
        return intersect.volume();
    }

    /**
     * Computes the union MBR of this MBR and the given MBR.
     *
     * @param mbr the MBR to be united with this MBR
     * @return the union MBR of this MBR and the given MBR
     */
    public HyperRectangle union(HyperRectangle mbr) {
        if (this._dimension != mbr._dimension)
            throw new IllegalArgumentException("This MBR and the given MBR need same dimensionality");

        float[][] otherPoints = mbr.getPoints();
        float[][] unionPoints = new float[_dimension][2];
        final float[] otherAttrs = mbr.getOptAttrs();
        float[] unionOptAttrs = new float[Constants.ATTR_DIMENSION];
        
        for (int i = 0; i < this._dimension; i++) {
            unionPoints[i][MIN_CORD] = Math.min(this.points[i][MIN_CORD], otherPoints[i][MIN_CORD]);
            unionPoints[i][MAX_CORD] = Math.max(this.points[i][MAX_CORD], otherPoints[i][MAX_CORD]);
        }
        
        for (int j = 0; j < Constants.ATTR_DIMENSION; j++) {
			unionOptAttrs[j] = Math.min(this.opt_attrs[j], otherAttrs[j]);
		}
        
        HyperRectangle union = new HyperRectangle(_dimension);
        union.setPoints(unionPoints);
        union.setOptAttrs(unionOptAttrs);
        return union;
    }

    @SuppressWarnings("unchecked")
	@Override
    public MbrDTO toDTO() {
        return new MbrDTO(points, opt_attrs);
    }
}
