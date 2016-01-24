package edu.caravane.guitare.gitobejct;

public class GitBlob extends GitObject{
	protected static String type = "blob";
	protected int index;
	protected byte[] data;
	
	
	public GitBlob(byte[] d) {
		this.data = d;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the datas from a blob object
	 */
	public byte[] getData() {
		return this.data;
	}

	@Override
	int getSize() {
		//T0D0
		return 0;
	}

	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the id of the object
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
		return GitBlob.type;
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
}
