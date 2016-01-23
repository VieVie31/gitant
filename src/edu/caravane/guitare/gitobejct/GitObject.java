package edu.caravane.guitare.gitobejct;

public abstract class GitObject {
	protected String type;
	protected int size, dataIndex;
	protected byte[] data;
	protected boolean dataSave;
	protected int index;
	
	abstract byte[] getData();
	
	abstract int getSize();
	
	abstract int getId();
}
