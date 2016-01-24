package edu.caravane.guitare.gitobejct;

public class GitCommit extends GitObject {
	protected static String type = "commit";
	protected String treeld;
	protected String[] parentListId;
	protected GitInfo autor;
	protected GitInfo commiter;
	protected int index;
	
	public GitCommit(String treeld, String[] parentLstId, GitInfo autor, GitInfo commiter) {
		this.treeld = treeld;
		this.parentListId = parentLstId;
		this.autor = autor;
		this.commiter = commiter;
	}

	@Override
	int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return The id of the object
	 */
	@Override
	int getId() {
		return this.index;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return The treeld of the object
	 */
	public String getTreeld(){
		return this.treeld;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return The list of id from the object's parents
	 */
	public String[] getParentListId() {
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
	 * Setter
	 * 
	 * @author Sylvain
	 *
	 * @return void
	 */
	@Override
	void setId(int i) {
		this.index = i;		
	}

}
