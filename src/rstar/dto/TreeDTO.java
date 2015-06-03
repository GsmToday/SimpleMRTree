package rstar.dto;

public class TreeDTO extends AbstractDTO {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8666177964687799733L;
	public int dimension;
    public int pagesize;
    public long rootPointer;

    public TreeDTO(int dimension, int pagesize, long rootPointer) {
        this.dimension = dimension;
        this.pagesize = pagesize;
        this.rootPointer = rootPointer;
    }
}
