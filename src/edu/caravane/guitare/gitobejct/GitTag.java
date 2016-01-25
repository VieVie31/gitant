package edu.caravane.guitare.gitobejct;

import java.util.ArrayList;

public class GitTag extends GitObject {
	protected static String type = "tag";
	protected int size;
	protected ArrayList<Byte> data;
	protected String tagName;
	protected GitInfo tagger;
	
	public GitTag(String tagName, int size) {
		this.tagName = tagName;
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
	int getSize() {
		return this.size;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the datas from a tag object
	 */
	public ArrayList<Byte> getData() {
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

	//Setter
	
	@Override
	void setSize(int size) {
		this.size = size;
	}
	
	public void setData(ArrayList<Byte> data) {
		this.data = data;
	}
	
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	public void setTagger(GitInfo tagger) {
		this.tagger = tagger;
	}
	
	//Body
	
	public void addData(Byte data) {
		this.data.add(data);
	}
	
	public void removeData(int index) {
		this.data.remove(index);
	}

}
