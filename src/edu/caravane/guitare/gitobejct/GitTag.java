package edu.caravane.guitare.gitobejct;

public class GitTag extends GitObject {
	protected static String type = "tag";
	protected int size;
	protected String tagName, sha1;
	protected String objHexId;
	protected GitInfo tagger;
	protected String tagType;
	protected byte[] data;

	public GitTag(int size, String sha1, String objHexId, String tagType,
			String tagName, GitInfo tagger, byte[] data) {
		this.tagName = tagName;
		this.size = size;
		this.sha1 = sha1;
		this.objHexId = objHexId;
		this.tagType = tagType;
		this.tagger = tagger;
		this.data = data;
	}

	/**
	 * Getter
	 *
	 * @author Sylvain
	 *
	 * @return the type of the object
	 */
	@Override
	public String getType() {
		return GitTag.type;
	}

	/**
	 * Getter
	 *
	 * @author Sylvain
	 *
	 * @return the size of the object
	 */
	@Override
	public int getSize() {
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
	public String getId() {
		return this.sha1;
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
	 * *Getter
	 * 
	 * @author Marvyn
	 * 
	 * @return the sha1 of the object tagged
	 */
	public String getObjHexId(){
		return this.objHexId;
	}

	public String toString() {
		String s = "";
		s += "object : " + objHexId + "\n";
		s += "tag type : " + tagType + "\n";
		s += "tag name : " + tagName + "\n";
		s += "tagger : " + tagger.toString() + "\n";
		s += new String(data);
		return s;
	}
}
