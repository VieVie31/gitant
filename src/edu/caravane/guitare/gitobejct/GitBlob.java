package edu.caravane.guitare.gitobejct;

import java.io.IOException;
import java.util.Arrays;
import java.util.zip.DataFormatException;

public class GitBlob extends GitObject {
	protected static String type = "blob";
	protected String path, sha1;
	protected int size;
	protected int index;
	
	public GitBlob(String sha1, int size, int index, String path) {
		this.size = size;
		this.path = path;
		this.index = index;
		this.sha1 = sha1;
	}
 
	// Getter
	
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the type of the object
	 */
	@Override
	String getType() {
		return GitBlob.type;
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
	 * @return the position where the data start
	 */
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * This function return a byet array containing the data of the blob object
	 * 
	 * @author VieVie31
	 *
	 * @return the data of blob object
	 */
	public byte[] getData() throws IOException, DataFormatException {
		byte[] arrayFileContent = BinaryFile.decompress(path); 
		return Arrays.copyOfRange(arrayFileContent, 
				getIndex(), arrayFileContent.length);
	}
	
	/**
	 * Getter
	 * 
	 * @author Sylvain
	 *
	 * @return the path of the object
	 */
	public String getPath() {
		return this.path;
	}
	
	//setter
	@Override
	void setSize(int size) {
		this.size = size;
	}
	
	@Override
	void setId(String sha1) {
		this.sha1 = sha1;
	}
	
	public void setindex(int index) {
		this.index = index;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
}
