package rstar.dto;

import java.util.ArrayList;

public class NodeDTO extends AbstractDTO {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2398736454870648143L;
	public ArrayList<Long> children;
    public MbrDTO mbr;
    public boolean isLeaf;
    public long parentId;

    public NodeDTO(long parentId, boolean leaf, MbrDTO mbr, ArrayList<Long> children) {
        this.parentId = parentId;
        isLeaf = leaf;
        this.mbr = mbr;
        this.children = children;
    }
}
