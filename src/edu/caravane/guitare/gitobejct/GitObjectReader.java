package edu.caravane.guitare.gitobejct;

import java.io.IOException;
import java.util.zip.DataFormatException;

//16h35 -> 16h46
public class GitObjectReader {
	protected String id;
	protected byte[] array;
	protected int index;
	
	private class DataObject<T> {
		public T obj;
		public int lenIndex;
	}
	
	public GitObjectReader(String path) 
			throws IOException, DataFormatException {
		String[] str = path.split("/");
		id = str[str.length - 2] + str[str.length - 1];
		
		array = BinaryFile.decompress(path);
		index = 0;
	}
	
	protected DataObject<String> extractType(int index) {
		return null;
	}
}
