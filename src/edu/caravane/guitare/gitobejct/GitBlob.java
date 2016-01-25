package edu.caravane.guitare.gitobejct;

public class GitBlob extends GitObject {
	protected static String type = "blob";
	protected String path;
	protected int size;
	protected int index;
	
	public GitBlob(String path, int size, int index) {
		this.size = size;
		this.path = path;
		this.index = index;
	}
 
	// Getter
	
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the type of the object
	 */
	@Override
	String getType() {
		return GitBlob.type;
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
	 * @return the position where the data start
	 */
	public int getIndex() {
		return this.index;
	}

	//TODO
	public int getId() {
		return 0;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the path of the object
	 */
	public String getPath() {
		return this.path;
	}
	
	//setter
	@Override
	void setSize(int size) {
		this.size = size;
	}
	
	public void setindex(int index) {
		this.index = index;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
}
