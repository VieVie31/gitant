package edu.caravane.guitare.gitobejct;

import java.io.IOException;
import java.util.Arrays;
import java.util.zip.DataFormatException;

//16h35 -> 16h46
//15h36 -> 16h20
public class GitObjectReader {
	protected String id;
	protected byte[] array;
	protected int index;
	
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
		String[] str = path.split("/");
		id = str[str.length - 2] + str[str.length - 1];
		
		array = BinaryFile.decompress(path);
		index = 0;
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
	protected DataObject<String> extractSafeString(int index) throws Exception { //this function has never be tested !!!
		int i = index;
		
		byte[] forbiden = {0x00, 0x0a, 0x3c, 0x3e};
		
		byte t = array[i];
		if (byteArrayContainsOneOfThose(forbiden, array[i]) 
				|| t == 0x20  || t == '.' || t == ',' || t == ':'
				|| t == ';' || t == 0x22 || t == 0x27)
			throw new Exception(); //msg d'erreur plus tard
		
		while (!byteArrayContainsOneOfThose(forbiden, array[i++]));
		
		return new DataObject<String>(
				new String(Arrays.copyOfRange(array, 0, i - 1)),
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
        while ('0' <= (char) array[i] && (char) array[i] <= '9')
            number = number * 10 + (array[i++] - '0');
        
        return new DataObject<Integer>(number, index, i - index);
	}
	
	public static void main(String[] args) throws Exception {
		//tests...
		GitObjectReader gitObjectReader;
		gitObjectReader = new GitObjectReader("/Users/mac/Desktop/test.bin");
		System.out.println(gitObjectReader.extractWhileNotSP(0).obj + "coucou");
		System.out.println(gitObjectReader.extractWhileNotSP(0).len);
		gitObjectReader.index += gitObjectReader.extractWhileNotSP(0).len;
		gitObjectReader.index ++; //ignore space
		System.out.println(gitObjectReader.extractBase10Number(gitObjectReader.index).obj);
		System.out.println(gitObjectReader.extractBase10Number(gitObjectReader.index).len);
	}
}
