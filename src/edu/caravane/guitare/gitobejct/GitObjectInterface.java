package edu.caravane.guitare.gitobejct;

public interface GitObjectInterface {
	public void setDate(GitDate date);
	public void addName(String name);
	public void addParent(String parentFileName);
	
	public String getDate();
	public String[] getNames();
	public String[] getParentFiles();
}
