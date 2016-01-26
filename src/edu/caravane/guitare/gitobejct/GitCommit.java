package edu.caravane.guitare.gitobejct;

import java.util.ArrayList;

public class GitCommit extends GitObject {
	protected static String type = "commit";
	protected int size;
	protected String treeId, sha1;
	protected ArrayList<String> parentListId;
	protected GitInfo autor;
	protected GitInfo commiter;
	protected byte[] data;
	
	public GitCommit(int size, String sha1, String treeId, 
			ArrayList<String> parentLstId, GitInfo autor, 
			GitInfo commiter, byte[] data) {
		this.treeId = treeId;
		this.parentListId = parentLstId;
		this.autor = autor;
		this.commiter = commiter;
		this.size = size;
		this.sha1 = sha1;
		this.data = data;
	}

	//Getter
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the type of the object
	 */
	@Override
	String getType() {
		return GitCommit.type;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the size of the object
	 */
	@Override
	int getSize() {
		return this.size;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the id of the object
	 */
	@Override
	String getId() {
		return this.sha1;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return The treeId of the object
	 */
	public String getTreeId(){
		return this.treeId;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return The list of id from the object's parents
	 */
	public ArrayList<String> getParentListId() {
		return this.parentListId;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return The autor of the commit
	 */
	public GitInfo getAutor() {
		return this.autor;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return The commiter of the commit
	 */
	public GitInfo getCommiter() {
		return this.commiter;
	}

	//Setter
	@Override
	void setSize(int size) {
		this.size = size;
	}
	
	@Override
	void setId(String sha1) {
		this.sha1 = sha1;
	}
	
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	
	public void setParentListId(ArrayList<String> parentListId) {
		this.parentListId = parentListId;
	}
	
	public void setAutor(GitInfo autor) {
		this.autor = autor;
	}
	
	public void setCommiter(GitInfo commiter) {
		this.commiter = commiter;
	}
	
	//body
	
	public void addParentId(String parentId) {
		this.parentListId.add(parentId);
	}
	
	public void removeParentId(int index) {
		this.parentListId.remove(index);
	}
	
	public String toString() {
		String s = String.format("tree : %s\n", treeId);
		for (String st : parentListId)
			s += String.format("parent : %s\n", st);
		s += "Author   : " + autor.toString() + "\n";
		s += "Commiter : " + commiter.toString() + "\n";
		s += new String(data);
		return s;
	}
}
