package edu.caravane.guitare.gitobejct;

public class GitTag extends GitObject{
	private String type = "tag";
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
