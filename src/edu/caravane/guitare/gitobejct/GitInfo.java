package edu.caravane.guitare.gitobejct;

public class GitInfo {
	protected final String name;
	protected final String mail;
	protected GitDate date;
	
	public GitInfo(String name, String mail) {
		this.name = name;
		this.mail = mail;
	}
	
	public GitInfo(String name, String mail, GitDate date) {
		this.name = name;
		this.mail = mail;
		this.date = date;
	}
	
	//Getter
	
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
	public String getMail() {
		return this.mail;
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the date
	 */
	public GitDate getDate() {
		return this.date;
	}
	
	//Body
	
	public String toString() {
		return String.format(name, mail);
	}
}
