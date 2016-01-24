package edu.caravane.guitare.gitobejct;

public class GitInfo {
	protected String name;
	protected String mail;
	protected int dateTimeStamp;
	protected int UTC;
	
	public GitInfo(String name, String mail) {
		this.name = name;
		this.mail = mail;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the username
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the user mail
	 */
	public String getMai() {
		return this.mail;
	}
	
	public String toString() {
		return String.format(name, mail);
	}
}
