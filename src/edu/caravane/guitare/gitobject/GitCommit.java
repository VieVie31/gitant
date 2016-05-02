package edu.caravane.guitare.gitobject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitCommit extends GitObject {
	protected static GitObjectType type = GitObjectType.COMMIT;
	protected int size;
	protected String treeId, sha1;
	protected ArrayList<String> parentListId;
	protected GitInfo autor;
	protected GitInfo commiter;
	protected byte[] data;

	/**
	 * Constructor
	 *
	 * @param size
	 * @param sha1
	 * @param treeId
	 * @param parentLstId
	 * @param autor
	 * @param commiter
	 * @param data
	 */
	public GitCommit(int size, String sha1, String treeId, ArrayList<String> parentLstId, GitInfo autor,
			GitInfo commiter, byte[] data) {
		this.treeId = treeId;
		this.parentListId = parentLstId;
		this.autor = autor;
		this.commiter = commiter;
		this.size = size;
		this.sha1 = sha1;
		this.data = data;
	}

	/**
	 * Constructor
	 *
	 * This second constructor is used for pack object
	 *
	 * @param size
	 * @param sha1
	 * @param data
	 */
	public GitCommit(long size, String sha1, String data) {
		this.size = (int) size;
		this.sha1 = sha1;
		this.setData(data);
	}

	/**
	 * Getter
	 *
	 * @author Sylvain
	 *
	 * @return The type of the object
	 */
	@Override
	public GitObjectType getType() {
		return GitCommit.type;
	}

	/**
	 * Getter
	 *
	 * @author Sylvain
	 *
	 * @return The size of the object
	 */
	@Override
	public int getSize() {
		return this.size;
	}

	/**
	 * Getter
	 *
	 * @author Sylvain
	 *
	 * @return The id of the object
	 */
	@Override
	public String getId() {
		return this.sha1;
	}

	/**
	 * Getter
	 *
	 * @author Sylvain
	 *
	 * @return The treeId of the object
	 */
	public String getTreeId() {
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
	 * @return The author of the commit
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

	public void addParentId(String parentId) {
		this.parentListId.add(parentId);
	}

	public void removeParentId(int index) {
		this.parentListId.remove(index);
	}

	/**
	 * This function is used to set the data for the object (only used with
	 * pack) It contain the treeId,the parentLstId,the autor,the commiter and
	 * the data
	 *
	 * @param data
	 *            contains
	 */
	private void setData(String data) {
		String author = null;
		String commiter = null;
		GitDate autDate = null;
		GitDate comDate = null;
		Pattern line = Pattern.compile("\n");
		String[] datab = line.split(data);

		int i = 0;
		this.treeId = datab[i].substring(5);
		++i;

		parentListId = new ArrayList<String>();
		while (datab[i].contains("parent")) {
			parentListId.add(datab[i].substring(7));
			++i;
		}

		Pattern name = Pattern.compile("[A-Z][a-z]+ [A-Z][a-z]+");
		Matcher aName = name.matcher(datab[i]);
		while (aName.find())
			author = aName.group();

		Pattern mail = Pattern.compile("<.*>");

		Matcher aMail = mail.matcher(datab[i]);
		while (aMail.find())
			author += " : " + aMail.group().substring(1, aMail.group().length() - 1);

		Pattern date = Pattern.compile("[0-9]* \\+[0-9]*");
		Matcher aDate = date.matcher(datab[i]);
		while (aDate.find()) {
			String[] aDat = aDate.group().split(" +");
			autDate = new GitDate(Integer.parseInt(aDat[0]), Integer.parseInt(aDat[1].substring(2)));
		}

		++i;

		Pattern Name = Pattern.compile("[A-Z][a-z]+ [A-Z][a-z]+");
		Matcher cName = Name.matcher(datab[i]);
		while (cName.find())
			commiter = cName.group();

		Pattern Mail = Pattern.compile("<.*>");

		Matcher cMail = Mail.matcher(datab[i]);
		while (cMail.find())
			commiter += " : " + cMail.group().substring(1, cMail.group().length() - 1);

		Pattern Date = Pattern.compile("[0-9]* \\+[0-9]*");
		Matcher cDate = Date.matcher(datab[i]);
		while (cDate.find()) {
			String[] cDat = cDate.group().split(" +");
			comDate = new GitDate(Integer.parseInt(cDat[0]), Integer.parseInt(cDat[1].substring(2)));
		}

		String aut[] = author.split(":");
		String com[] = commiter.split(":");
		this.autor = new GitInfo(aut[0], aut[1].substring(1), autDate);
		this.commiter = new GitInfo(com[0], com[1].substring(1), comDate);
		i += 2;
		this.data = datab[i].getBytes();
	}

	public String toString() {
		// StringBuilder est votre ami
		String s = String.format("tree : %s\n", treeId);
		for (String st : parentListId)
			s += String.format("parent : %s\n", st);
		s += "Author   : " + autor.toString() + "\n";
		s += "Commiter : " + commiter.toString() + "\n";
		s += new String(data);
		return s;
	}
}