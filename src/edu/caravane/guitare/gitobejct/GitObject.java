package edu.caravane.guitare.gitobejct;

public abstract class GitObject {
	protected String type, sha1;
	protected int size;
	protected boolean dataSave;
	
	abstract String getType();
	abstract int getSize();
	abstract String getId();
	abstract void setSize(int size);
	abstract void setId(String sha1);
}
