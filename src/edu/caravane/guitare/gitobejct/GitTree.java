package edu.caravane.guitare.gitobejct;
import java.util.*;

public class GitTree extends GitObject {
	protected static String type = "tree";
	protected int size;
	protected String sha1;
	protected ArrayList<TreeEntry> lstEntr;
	
	public GitTree(int size, String sha1, ArrayList<TreeEntry> lstEntr) {
		this.lstEntr = lstEntr;
		this.size = size;
		this.sha1 = sha1;
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
	 * @return the id of the object
	 */
	@Override
	String getId() {
		return this.sha1;
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
	
	
	public void setListEntry(ArrayList<TreeEntry> listEntry) {
		this.lstEntr = listEntry;
	}
	
	//Body
	
	public void addEntry(TreeEntry te) {
		this.lstEntr.add(te);
	}

}
