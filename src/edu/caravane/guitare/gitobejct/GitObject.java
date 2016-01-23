package edu.caravane.guitare.gitobejct;

public abstract class GitObject {
	
	//10h30 -> 10h40
	protected String type;
	protected int size, dataIndex;
	protected byte[] data;
	protected boolean dataSave;
	protected int index;
	
	abstract byte[] getData();
	
	abstract int getSize();
	
	abstract int getId();
}
