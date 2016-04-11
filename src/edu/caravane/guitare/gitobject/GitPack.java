package edu.caravane.guitare.gitobject;

import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;

import org.eclipse.jgit.internal.storage.file.PackFile;
import org.eclipse.jgit.internal.storage.file.PackIndex.MutableEntry;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

public class GitPack {

	private static Repository repository;
	protected final static String osBarre =
			System.getProperty("os.name").charAt(0) == 'W' ? "\\\\" : "/" ;

/**
 * Function used to treat pack object,
 * it load the data from each object of the pack to create the original object
 * when the object is recreate it store the object into the git object index
 *
 * @param pathPack
 * @throws IOException
 * @throws DataFormatException
 */
	public static void makePack(String pathPack) throws IOException,
	DataFormatException {
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
			String sha1 = mutableEntry.toObjectId().name();
			ObjectId id = repository.resolve(sha1);
			ObjectLoader loader = repository.open(id);

			switch (loader.getType()) {
			case Constants.OBJ_BLOB:
				obj = new GitBlob(loader.getSize(), sha1, "",
						loader.getCachedBytes());
				break;
			case Constants.OBJ_COMMIT:
				obj = new GitCommit(loader.getSize(), sha1,
						new String(loader.getCachedBytes()));
				break;
			case Constants.OBJ_TREE:
				obj = new GitTree(loader.getSize(), sha1,
						loader.getBytes());
				break;
			case Constants.OBJ_TAG:
				obj = new GitTag(loader.getSize(), sha1,
						new String(loader.getCachedBytes()));
			default:
				break;
			}

				goi.put(mutableEntry.toObjectId().name(),obj);
		}
	}
}