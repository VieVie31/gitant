package edu.caravane.guitare.gitobejct;
import java.util.*;

public class GitTree extends GitObject {
	protected static String type = "tree";
	protected int size;
	protected ArrayList<TreeEntry> lstEntr;
	
	public GitTree(ArrayList<TreeEntry> lstEntr, int size) {
		this.lstEntr = new ArrayList<TreeEntry>();
		this.size = size;
	}
	
	//Getter
	
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
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the size of the object
	 */
	@Override
	int getSize() {
		return this.size;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return The list of the entry
	 */
	public ArrayList<TreeEntry> listEntry() {
		return this.lstEntr;
	}
	
	//Setter
	
	@Override
	void setSize(int size) {
		this.size = size;
	}
	
	public void setListEntry(ArrayList<TreeEntry> listEntry) {
		this.lstEntr = listEntry;
	}
	
	//Body
	
	public void addEntry(TreeEntry te) {
		this.lstEntr.add(te);
	}

}
