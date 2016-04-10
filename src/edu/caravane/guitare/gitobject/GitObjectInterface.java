package edu.caravane.guitare.gitobject;

public interface GitObjectInterface {
	public void setDate(GitDate date);
	public void addName(String name);
	public void addParent(String parentFileName);
	public void addParents(String[] parentsFilesNames);
	
	public String getDate();
	public String[] getNames();
	public String[] getParentFiles();
}
