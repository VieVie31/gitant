package edu.caravane.guitare.gitobejct;

public abstract class GitObject {
	protected String type, sha1;
	protected int size;

	abstract String getType();
	abstract int getSize();
	abstract String getId();
}
