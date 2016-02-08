package edu.caravane.guitare.gitobejct;

import java.util.HashMap;

public class GitObjectsIndex {
	protected static GitObjectsIndex instance;
	protected HashMap<String, GitObject> hashMap;

	public static GitObjectsIndex getInstance() {
		if (instance == null)
			instance = new GitObjectsIndex();
		return instance;
	}

	private GitObjectsIndex() {
		hashMap = new HashMap<String, GitObject>();
	}

	/**
	 * This function retrieve a git object (already indexed) by his hash (sha1)
	 *
	 * @author VieVie31
	 *
	 * @param  sha1 the hash of the git object to retrieve
	 * @return a git object
	 */
	public GitObject get(String sha1) {
		return hashMap.get(sha1);
	}

	/**
	 * This function put (index) a git object by his hash value (sha1)
	 *
	 * @author VieVie31
	 *
	 * @param sha1 the hash of the git object
	 * @param gitObject the git object
	 */
	public void put(String sha1, GitObject gitObject) {
		hashMap.put(sha1, gitObject);
	}
}
