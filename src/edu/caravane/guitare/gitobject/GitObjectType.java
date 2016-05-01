package edu.caravane.guitare.gitobject;

public enum GitObjectType {
	BLOB("blob"), COMMIT("commit"), TAG("tag"), TREE("tree");

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

	public String getName() {
		return name;
	}

	public static GitObjectType findFromName(String name) {
		GitObjectType[] values = GitObjectType.values();
		for (int i = 0; i < values.length; i++) {
			GitObjectType array_element = values[i];
			if (array_element.getName().equals(name)) {
				return array_element;
			}
		}
		return null;
	}
}
