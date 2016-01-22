package edu.caravane.guitare.gitobejct;

import java.io.ByteArrayOutputStream;  
import java.io.IOException; 
import java.nio.file.Paths;
import java.nio.file.Files; 
import java.util.zip.DataFormatException;  
import java.util.zip.Inflater; 

//13h55 -> 14h05
//16h00 -> 16h18
/**
 * 
 * @author VieVie31
 *
 */
public class BinaryFile {
	/**
	 * Return the content of the file specified by the path argument as
	 * a byte array.
	 * 
	 * @author VieVie31
	 * 
	 * @param  path the path of the binary file to read
	 * @return the content of the file
	 * @throws IOException
	 */
	public static byte[] read(String path) throws IOException {
		return Files.readAllBytes(Paths.get(path)); //java7 way
	}

	/**
	 * Return the decompressed version of the input array using the zlib 
	 * algorithm.
	 * 
	 * @author VieVie31
	 * 
	 * @param  data an input byte array to decompress using zlib
	 * @return the data array decompressed
	 * @throws IOException
	 * @throws DataFormatException
	 */
	public static byte[] decompress(byte[] data) 
			throws IOException, DataFormatException {
		Inflater inflater = new Inflater();
		inflater.setInput(data);

		ByteArrayOutputStream outputStream;
		outputStream = new ByteArrayOutputStream(data.length);  
		byte[] buffer = new byte[1024];  

		while (!inflater.finished())
			outputStream.write(buffer, 0, inflater.inflate(buffer)); 

		outputStream.close();  
		return outputStream.toByteArray();
	}

	/**
	 * Return a decompressed file content, using the zlib algorithm ,
	 * as byte array.
	 * 
	 * @author VieVie31
	 * 
	 * @param  path of the binary file to decompress
	 * @return the content of the file decompressed
	 * @throws IOException
	 * @throws DataFormatException
	 */
	public static byte[] decompress(String path) 
			throws IOException, DataFormatException {
		return decompress(read(path));
	}
}
