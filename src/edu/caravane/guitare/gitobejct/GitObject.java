package edu.caravane.guitare.gitobejct;

import java.util.ArrayList;

public abstract class GitObject implements GitObjectInterface {
	protected String type, sha1;
	protected int size;
	
	protected ArrayList<String> names = new ArrayList<String>();
	protected ArrayList<String> parents = new ArrayList<String>();
	protected GitDate date = new GitDate(0); //What I haven't for 14/02

	public abstract String getType();
	public abstract int getSize();
	public abstract String getId();
	
	public GitDate date() {
		return date;
	}
	
	@Override
	public void setDate(GitDate date) {
		this.date = date;
	}
	
	@Override
	public void addName(String name) {
		names.add(name);
	}

	@Override
	public void addParent(String parentFileName) {
		parents.add(parentFileName);
	}
	
	@Override
	public void addParents(String[] parentsFilesNames) {
		for (String parent : parentsFilesNames)
			if (!parents.contains(parent))
				parents.add(parent);
	}

	@Override
	public String getDate() {
		return date.toString();
	}

	@Override
	public String[] getNames() {
		return (String[]) names.toArray();
	}

	@Override
	public String[] getParentFiles() {
		return (String[]) parents.toArray();
	}
}
