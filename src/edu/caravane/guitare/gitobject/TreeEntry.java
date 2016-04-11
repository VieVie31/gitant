package edu.caravane.guitare.gitobject;

public class TreeEntry {
	protected final int octalMode;
	protected final String name;
	protected final String sha1;

	//ne pas faire d'autre constructeur pour que ne soient
	//instancies que les objects complets
	public TreeEntry(int octalMode, String name, String sha1) {
		this.octalMode = octalMode;
		this.name = name;
		this.sha1 = sha1;
	}

	//ne pas faire de setter car cet object une fois intancie
	//doit etre en read only

	/**
	 * Getter
	 *
	 * @author VieVie31
	 *
	 * @return the octal mode of a file
	 */
	public int getOctalMode() {
		return this.octalMode;
	}

	/**
	 * Getter
	 *
	 * @author VieVie31
	 *
	 * @return the name of a file
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Getter
	 *
	 * @author VieVie31
	 *
	 * @return the sha1 of a file
	 */
	public String getSha1() {
		return this.sha1;
	}

	public String toString() {
		return String.format("%06o %s %s", octalMode, name, sha1);
	}
}
