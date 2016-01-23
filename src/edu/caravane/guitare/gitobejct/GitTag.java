package edu.caravane.guitare.gitobejct;

public class GitTag extends GitObject{
	
	//10h45 -> 11h03
	private String type = "Tag";
	private String tagName;
	private byte[] data;
	private int index;
	
	public int getId() {
		return this.index;
	}

	@Override
	byte[] getData() {
		return this.data;
	}

	@Override
	int getSize() {
		return 0;
	}
}
