package edu.caravane.guitare.gitobejct;

public class GitTag extends GitObject{
	protected static String type = "tag";
	protected String tagName;
	protected byte[] data;
	protected int index;
	protected GitInfo tagger;
	
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
	 * @return the datas from a tag object
	 */
	public byte[] getData() {
		return this.data;
	}

	@Override
	int getSize() {
		return 0;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the tag name from a tag object
	 */
	public String getTagName() {
		return this.tagName;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the tagger of a tag object
	 */
	public GitInfo getTagger() {
		return this.tagger;
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
		return GitTag.type;
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
