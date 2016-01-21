package edu.rissermaroix.olivier.gitobejct;

import java.io.ByteArrayOutputStream;  
import java.io.IOException; 
import java.nio.file.Paths;
import java.nio.file.Files; 
import java.util.zip.DataFormatException;  
import java.util.zip.Inflater; 

//13h55 -> 14h05
public class BinaryFile {
	public static byte[] read(String path) throws IOException {
		return Files.readAllBytes(Paths.get(path)); //java7 way
	}
	
	public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
		Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);  
        byte[] buffer = new byte[1024];  

        while (!inflater.finished()) {  
            int count = inflater.inflate(buffer);  
            outputStream.write(buffer, 0, count);  
        }  

        outputStream.close();  
        return outputStream.toByteArray();
	}
	
	public static byte[] decompress(String path) throws IOException, DataFormatException {
		return decompress(read(path));
	}
	
	public static void main(String[] args) {
		System.out.println("coucou");
	}
}
