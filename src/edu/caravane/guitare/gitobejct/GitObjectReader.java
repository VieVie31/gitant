package edu.caravane.guitare.gitobejct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.DataFormatException;


public class GitObjectReader {
	protected String id;
	protected byte[] array;
	protected int index;
	protected String type;
	protected String path;
	protected boolean isGitObject;
	
	/**
	 * Simple structure containing an extracted object, 
	 * the start position in the array for decoding,
	 * the len of this object written in the array.
	 * 
	 * @author VieVie31
	 *
	 * @param <T> the type of object decoded
	 */
	protected class DataObject<T> {
		public T obj;
		public int startIndex;
		public int len;
		
		public DataObject(T object, int startPositionIndex, int length) {
			this.obj = object;
			this.startIndex = startPositionIndex;
			this.len = length;
		}
	}
	
	public GitObjectReader(String path) 
			throws IOException, DataFormatException {
		this.path = path;
		
		String[] str = path.split("/");
		id = str[str.length - 2] + str[str.length - 1];
		
		array = BinaryFile.decompress(path);
		index = 0;
		
		type = extractWhileNotSP(0).obj;
		isGitObject = isBlob() || isTree() || isTag() || isCommit();
	}
	
	/**
	 * This funciton extract all symbols in the array fron the index position
	 * until the symbol encontred is <SP> (space char).
	 * 
	 * @author VieVie31
	 *
	 * @param  index position for starting to decode
	 * @return a DataObject containing the decoded object
	 */
	protected DataObject<String> extractWhileNotSP(int index) {
		int i = index;
		
		while (array[i++] != (byte) ' ');
		
        return  new DataObject<String>(
        			new String(Arrays.copyOfRange(array, 0, i - 1)),
        			index, i - index - 1);
	}
	
	/**
	 * This function return a safeString.
	 * A safe string is a sequence of bytes not containing the ASCII charcter
	 * byte values :
	 * 		<NULL> (0x00), <LF> (0x0a), '<' (0x3c), or '>' (0x3e),
	 * and the sequence may not begin or end with any bytes with the following
	 * ASCII charcter byte values : 
	 * 		<SP> (0x20), '.' (0x2e), ',' (0x2c), ':' (0x3a), ';' (0x3b),
	 * 		'<'  (0x3c), '>' (0x3e), '"' (0x22), "'" (0x27).
	 * 
	 * @author VieVie31
	 *
	 * @param  index position for starting to decode
	 * @return a DataObject containing the safeString
	 */
	protected DataObject<String> extractSafeString(int index) throws Exception {
		int i = index;
		
		byte[] forbiden = {0x00, 0x0a, 0x3c, 0x3e};
		
		byte t = array[i];
		if (byteArrayContainsOneOfThose(forbiden, array[i]) 
				|| t == 0x20  || t == '.' || t == ',' || t == ':'
				|| t == ';' || t == 0x22 || t == 0x27)
			throw new Exception(); //msg d'erreur plus tard
		
		while (i < array.length && //plutot important
				!byteArrayContainsOneOfThose(forbiden, array[i++]));
		
		return new DataObject<String>(
				new String(Arrays.copyOfRange(array, index, i - 1)),
				index, i - index - 1);
	}
	
	/**
	 * This function check if a specific byte is in a set of bytes.
	 * 
	 * @author VieVie31
	 *
	 * @param  bts the set of bytes
	 * @param  testIfIn the byte to check if bts contains it
	 * @return true if testIfIn is in bts else false
	 */
	protected boolean byteArrayContainsOneOfThose(byte[] bts, byte testIfIn) {
		for (byte b: bts)
			if (b == testIfIn)
				return true;
		
		return false;
	}
	
	/**
	 * This function extract a number written in base 10,
	 * using the ASCII encoding, and return this number as an Integer
	 * in a DataObject
	 * 
	 * @author VieVie31
	 *
	 * @param  index position for start to decode
	 * @return a DataObject containing the decoded integer
	 */
	protected DataObject<Integer> extractBase10Number(int index) {
		int i  = index;
		
		Integer number = 0;
        while (i < array.length && //plutot important
        		'0' <= (char) array[i] && (char) array[i] <= '9')
            number = number * 10 + (array[i++] - '0');
        
        return new DataObject<Integer>(number, index, i - index);
	}
	
	/**
	 * This function return the nex 20 bytes and return the reprenseation as
	 * string in lower case.
	 * 
	 * @author VieVie31
	 *
	 * @param  index the starting position for extraction
	 * @return a DataObject containing the SAH1 as string in lower case
	 */
	protected DataObject<String> extractSHA1Binary(int index) {	
		int i = index;
		StringBuilder sb = new StringBuilder();
		
		for (; i < index + 20; i++)
			sb.append(String.format("%02x", (byte) array[i] & 0xff));
		
		return new DataObject<String>(sb.toString(), index, i - index);
	}
	
	/**
	 * This function return the next 40 chars and return the representation as
	 * String in lower case.
	 * 
	 * @author VieVie31
	 *
	 * @param  index for starting to extract the sha1
	 * @return a DataObject containing the SHA1 as string in lower case
	 * @throws an exception if the chars are not in hexa (lower case)
	 */
	protected DataObject<String> extractSHA1String(int index) throws Exception {
		int i = index;
		
		for(; i < index + 40; i++)
			if ((array[i] > '9' || array[i] < '0') 
					&& (array[i] < 'a' || array[i] > 'f'))
				throw new Exception(); //message d'erreur plus tard
		
		return new DataObject<String>(
				new String(Arrays.copyOfRange(array, index, index + 40)),
				index, 40);
	}
	
	/**
	 * This function return the chmod of a file (using the octal mode)
	 * 
	 * @author VieVie31
	 *
	 * @param  index for starting to decode
	 * @return
	 */
	protected DataObject<Integer> extractFileGitCHMOD(int index) {
		int i = index;
		
		Integer number = 0;
		while (i < array.length //plutot important
				&& '0' <= (char) array[i] && (char) array[i] <= '9')
			number = (number << 3) | ((array[i++] - '0') & 0x0f);
		
		return new DataObject<Integer>(number, index, i - index);
	}
	
	
	/**
	 * This function extract the next tree entry from the index.
	 * A tree entry is composed by :
	 * <CHMOD> <SP> <FILE_NAME> <NULL> <BINARY_SHA1_ID>
	 * 
	 * @author VieVie31
	 *
	 * @param  index for starting to decode
	 * @return a tree entry
	 * @throws Exception
	 */
	protected DataObject<TreeEntry> extractTreeEntry(int index) 
			throws Exception {
		int i = index;
		
		DataObject<Integer> chmod;
		DataObject<String> name;
		DataObject<String> sha1;
		
		chmod = extractFileGitCHMOD(i);
		i += chmod.len;
		
		i++; //+1 pour l'espace
		name = extractSafeString(i);
		i += name.len;
		i++; //+1 pour <NULL>
		
		sha1 = extractSHA1Binary(i);
		
		return new DataObject<TreeEntry>(
				new TreeEntry(chmod.obj, name.obj, sha1.obj),
				index, i + sha1.len - index);
	}
	
	
	/**
	 * This function extract all the tree entries from the index to the
	 * end of the arrray.
	 * 
	 * @author VieVie31
	 *
	 * @param  index for starting to decode
	 * @return an ArrayList made of TreeEntry
	 * @throws Exception
	 */
	protected ArrayList<TreeEntry> extractTreeEntries(int index) 
			throws Exception {
		ArrayList<TreeEntry> tList = new ArrayList<TreeEntry>();
		
		int i = index;
		
		while (i < array.length - 20) {
			DataObject<TreeEntry> doTreeEntry = this.extractTreeEntry(i);
			i += doTreeEntry.len;
			tList.add(doTreeEntry.obj);
		}
		
		return tList;
	}
	
	
	/**
	 * This function return the time zone offset of a date in secondes.
	 * For example : +0100 means : +01h00 
	 * 
	 * @author VieVie31
	 *
	 * @param  index for starting to decode
	 * @return the offset in seconds.
	 */
	protected DataObject<Integer> extractTzOffset(int index) {
		int i = index;
		
		i++; //ignore the sign the sign for the moment...
		DataObject<Integer> tzOffset = extractBase10Number(i);
		int mins = tzOffset.obj % 100;
		int hours = tzOffset.obj / 100;
		tzOffset.obj = hours * 3600 + mins * 60; //convert to seconds
		
		return new DataObject<Integer>(
				tzOffset.obj * ((array[index] == '+') ? 1 : -1), //take the sign
				index, 1 + tzOffset.len); //+1 for the sign
	}
	
	/**
	 * This function return the firsts sequence of char not containing <SP>
	 * 
	 * @author VieVie31
	 *
	 * @return the type of the git object if is a git object else random bytes
	 */
	public String getType() {
		return type;
	}
	
	
	/**
	 * This function return the size of the git object
	 * 
	 * @author VieVie31
	 *
	 * @return the size of the git object
	 */
	public int getSize() {
		return extractBase10Number(getType().length() + 1).obj;
	}
	
	
	/**
	 * This function return the id of the git object
	 * 
	 * @author VieVie31
	 *
	 * @return the id of the git object
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * This function return the position of the first byte after the header.
	 * 
	 * @author VieVie31
	 *
	 * @return the index position for decoding content.
	 */
	public int getContentIndex() {
		return getType().length() + 1 // <SP>
				+ (int) (Math.log10(getSize()) + 1) //the len of size in str
				+ 1; // <NULL>
	}
	
	
	/**
	 * This function says if the byte array is a git object.
	 * 
	 * @author VieVie31
	 *
	 * @return true if the header is one of a git object else false
	 */
	public boolean isGitObject() {
		return isGitObject;
	}
	
	
	/**
	 * This function says if the byte array is a Blob object.
	 * 
	 * @author VieVie31
	 *
	 * @return true if the array is a blob else false
	 */
	public boolean isBlob() {
		return getType().equals("blob");
	}
	
	
	/**
	 * This function says if the byte array is a Tree object.
	 * 
	 * @author VieVie31
	 *
	 * @return true if the array is a tree object else false.
	 */
	public boolean isTree() {
		return type.equals("tree");
	}
	
	
	/**
	 * This function says if the byte array is a Tag object.
	 * 
	 * @author VieVie31
	 *
	 * @return true if the array is a tag object.
	 */
	public boolean isTag() {
		return type.equals("tag");
	}
	
	
	/**
	 * This function says if the byte array is a Commit object.
	 * 
	 * @author VieVie31
	 *
	 * @return true if the array is a commit array else false.
	 */
	public boolean isCommit() {
		return type.equals("commit");
	}
	
	
	/**
	 * This function decode the git tree objects and return an instance of
	 * the tree decoded.
	 * 
	 * @author VieVie31
	 *
	 * @return a GitTree decoded from this sequence of bytes
	 * @throws Exception if the object is not a tree
	 */
	public GitTree buildTree() throws Exception {
		if (!getType().equals("tree"))
			throw new Exception(); //message plus tard
		
		return new GitTree(getSize(), getId(), 
				extractTreeEntries(getContentIndex()));
	}
	
	/**
	 * This function decode the git blob object and return an instnace of 
	 * the blob decoded.
	 * 
	 * @author VieVie31
	 *
	 * @return
	 * @throws Exception
	 */
	public GitBlob buildBlob() throws Exception {
		if (!getType().equals("blob"))
			throw new Exception(); //message plus tard
		
		return new GitBlob(getId(), getSize(), getContentIndex(), path);
	}
	
	public static void main(String[] args) throws Exception {
		//tests...
		
		GitObjectReader gor;
		gor = new GitObjectReader("Annexes/tests/test_tree.bin");
		
		
		GitTree gitTree = gor.buildTree();
		System.out.println("Test TreeObject...");
		System.out.println(String.format("Size : %d", gitTree.getSize()));
		for (TreeEntry tEntry : gitTree.listEntry())
			System.out.println(tEntry);
		
		
		System.out.println("---------");
		
		gor = new GitObjectReader("Annexes/tests/test_blob.bin");
		
		GitBlob gitBlob = gor.buildBlob();
		System.out.println("Test BlobObject...");
		System.out.println(String.format("Size : %d", gitBlob.getSize()));
		System.out.println(String.format("Index position data : %d", 
				gitBlob.getIndex()));
		
	}
}
