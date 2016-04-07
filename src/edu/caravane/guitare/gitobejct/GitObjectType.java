package edu.caravane.guitare.gitobejct;

public enum GitObjectType {
	BLOB("blob"),
	COMMIT("commit"),
	TAG("tag"),
	TREE("tree");

	private final String name;
	
	private GitObjectType(String s) {
		name = s;
	}
	
	public boolean equals(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}
	
	public String toString() {
		return this.name;
	}
}
