package edu.caravane.guitare.gitobejct;

public abstract class GitObject {
	protected String type;
	protected int size;
	protected boolean dataSave;
	
	abstract String getType();
	abstract int getSize();
	abstract void setSize(int size);
}
