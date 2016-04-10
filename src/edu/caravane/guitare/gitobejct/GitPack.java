package edu.caravane.guitare.gitobejct;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;

import org.eclipse.jgit.internal.storage.file.PackFile;
import org.eclipse.jgit.internal.storage.file.PackIndex.MutableEntry;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.transport.PackParser;

public class GitPack {

	private static Repository repository;
	protected final static String osBarre =
			System.getProperty("os.name").charAt(0) == 'W' ? "\\\\" : "/" ;

	public static void makePack(String pathPack) throws IOException, DataFormatException {
		GitObjectReader gor;
		GitObjectsIndex goi =  GitObjectsIndex.getInstance();
		String[] path = pathPack.split(osBarre+".git"+osBarre);
		RepositoryBuilder builder = new RepositoryBuilder();
		builder.setMustExist(true);
		builder.setGitDir(new File(path[0]+ osBarre+".git"+osBarre));
		repository = builder.build();
		File pa = new File(pathPack);
		PackFile p = new PackFile(pa, 0);
		for (MutableEntry mutableEntry : p) {
			GitObject obj = null;
			ObjectId id = repository.resolve(mutableEntry.toObjectId().name());
			ObjectLoader loader = repository.open(id);
			switch (loader.getType()) {
			case Constants.OBJ_BLOB:
				obj = new GitBlob(mutableEntry.toObjectId().name(), loader.getSize(), "", loader.getCachedBytes());
				break;
			/*case Constants.OBJ_COMMIT:
				obj = new GitCommit(loader.getSize(), mutableEntry.toObjectId().name(), new String(loader.getBytes()));
				break;
			case Constants.OBJ_TREE:
				break;
			case Constants.OBJ_TAG:
				obj = new GitTag(loader.getSize(), mutableEntry.toObjectId().name(), new String(loader.getCachedBytes()));*/
			default:
				break;
			}
			if (loader.getType() == Constants.OBJ_BLOB) {
				goi.put(mutableEntry.toObjectId().name(),obj);
			}
		}
	}
}