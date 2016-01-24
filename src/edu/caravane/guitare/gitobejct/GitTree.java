package edu.caravane.guitare.gitobejct;
import java.util.*;

public class GitTree extends GitObject {
	protected static String type = "tree";
	protected int index;
	protected ArrayList<TreeEntry> lstEntr;
	
	public GitTree() {
		lstEntr = new ArrayList<TreeEntry>();
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return The list of the entry
	 */
	public ArrayList<TreeEntry> getData() {
		return this.lstEntr;
	}

	@Override
	int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return The id of the object
	 */
	@Override
	int getId() {
		return this.index;
	}

	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the type of the object
	 */
	@Override
	String getType() {
		return GitTree.type;
	}
	
	/**
	 * Setter
	 * 
	 * @author Sylvain
	 *
	 * @return void
	 */
	@Override
	void setId(int i) {
		this.index = i;	
	}
	
	public void addEntry(TreeEntry te) {
		this.lstEntr.add(te);
	}

}
