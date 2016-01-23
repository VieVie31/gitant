package edu.caravane.guitare.gitobejct;

public class GitBlob extends GitObject{
	
	//10h40 -> 10h45 sylvain
	private String type = "blob";
	private int index;
	
	@Override
	byte[] getData() {
		return null;
	}

	@Override
	int getSize() {
		return 0;
	}

	@Override
	int getId() {
		return this.index;
	}
}
