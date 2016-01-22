package edu.caravane.guitare.gitobejct;

import java.util.HashMap;

//18h19 -> 18h26
public class GitObjectsIndex {
	protected HashMap<String, GitObject> hashMap;
	
	public GitObjectsIndex() {
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
