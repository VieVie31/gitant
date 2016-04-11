package edu.caravane.guitare.gitobject;

import java.io.IOException;
import java.util.Arrays;
import java.util.zip.DataFormatException;

/**
 *
 * @author Sylvain
 *
 */
public class GitBlob extends GitObject {
	protected static GitObjectType type = GitObjectType.BLOB;
	protected String path, sha1;
	protected int size;
	protected int index;
	protected byte[] data;

	/**
	 *Constructor
	 *
	 * @param sha1
	 * @param size
	 * @param index, it's the beginning of the data
	 * @param path
	 */
	public GitBlob(String sha1, int size, int index, String path) {
		this.size = size;
		this.path = path;
		this.index = index;
		this.sha1 = sha1;
	}

/**
 * Constructor
 *
 * This second constructor is used for pack object
 *
 * @param size
 * @param sha1
 * @param path
 * @param data
 */
	public GitBlob(long size, String sha1, String path, byte[] data) {
		this.size = (int) size;
		this.path = path;
		this.index = 0;
		this.sha1 = sha1;
		this.data = data;
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
		return GitBlob.type;
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
	 * @return The position where the data start
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * Getter
	 *
	 * This function return a byte array containing the data of the blob object
	 *
	 * @author VieVie31
	 *
	 * @return the data of blob object
	 */
	public byte[] getData() throws IOException, DataFormatException {
		if (path.contains("object")) {
		byte[] arrayFileContent = BinaryFile.decompress(path);
		return Arrays.copyOfRange(arrayFileContent,
				getIndex(), arrayFileContent.length);
		} else {
			return this.data;
		}
	}

	/**
	 * Getter
	 *
	 * @author Sylvain
	 *
	 * @return The path of the object
	 */
	public String getPath() {
		return this.path;
	}
}