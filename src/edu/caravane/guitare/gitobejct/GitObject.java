package edu.caravane.guitare.gitobejct;

public abstract class GitObject {
	protected String type;
	protected int size, dataIndex, index;
	protected boolean dataSave;
	
	abstract int getSize();
	abstract int getId();
	abstract String getType();
	abstract void setId(int i);
}
