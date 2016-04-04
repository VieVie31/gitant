package edu.caravane.guitare.gitobejct;

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

	public static void makePack(String pathPack) throws IOException, DataFormatException {
		String[] path = pathPack.split(osBarre+".git"+osBarre);
		System.out.println(path[0]);
		RepositoryBuilder builder = new RepositoryBuilder();
		builder.setMustExist(true);
		builder.setGitDir(new File(path[0]+ osBarre+".git"+osBarre));
		repository = builder.build();
		File pa = new File(pathPack);
		PackFile p = new PackFile(pa, 0);
		for (MutableEntry mutableEntry : p) {
			ObjectId id = repository.resolve(mutableEntry.toObjectId().name());
			ObjectLoader loader = repository.open(id);
			switch (loader.getType()) {
			case Constants.OBJ_COMMIT:
				System.out.println(mutableEntry.toObjectId().name());
				System.out.println("Commit");
				break;
			case Constants.OBJ_TREE:
				System.out.println(mutableEntry.toObjectId().name());
				System.out.println("Tree");
				break;
			case Constants.OBJ_BLOB:
				System.out.println(mutableEntry.toObjectId().name());
				System.out.println("Blob");
				break;
			case Constants.OBJ_TAG:
				System.out.println(mutableEntry.toObjectId().name());
				System.out.println("Tag");
				break;
			}

		}
	}
}
