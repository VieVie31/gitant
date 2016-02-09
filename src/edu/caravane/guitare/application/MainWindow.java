package edu.caravane.guitare.application;

import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.*;
import edu.caravane.guitare.gitobejct.GitCommit;
import edu.caravane.guitare.gitobejct.GitObject;
import edu.caravane.guitare.gitobejct.GitObjectReader;
import edu.caravane.guitare.gitobejct.GitObjectsIndex;
import edu.caravane.guitare.gitobejct.GitTree;
import edu.caravane.guitare.gitobejct.TreeEntry;
import javafx.application.Application;

public class MainWindow extends Application {
	protected GitObjectsIndex gitObjectsIndex;

	/**
	 * This function add all the objects into a GitObjectsIndex object
	 *
	 * @author Sylvain, VieVie31
	 *
	 * @param listObjs a list of paths (String) of git object
	 * @return the GitObjectsIndex containing all the objects by sha1
	 * @throws Exception if something wrong appends
	 */
	public GitObjectsIndex indexObjects(String[] listObjs) throws Exception {
			GitObjectReader gor;
			GitObjectsIndex goi =  GitObjectsIndex.getInstance();

			for (String pathObj : listObjs) {
				//on ne traite pas les pack pour le moment
				if (pathObj.contains("/pack/"))
					continue;
				
				gor = new GitObjectReader(pathObj);
				goi.put(gor.getId(), gor.builGitObject());
			}
			
			return goi;
	}

	public void makeLinks() {
		/* fait chier... on verra ca demain...
		GitObject gitObject = null;
		GitObjectsIndex gitObjectsIndex = GitObjectsIndex.getInstance();
		//on gere les commits en premier
		for (String sha1GitObject : gitObjectsIndex.getListOfAllObjectKeys()) {
			gitObject = gitObjectsIndex.get(sha1GitObject);
			
			if (!gitObject.getType().equals("commit"))
				continue;
			
			GitCommit gc = (GitCommit) gitObject;
			GitTree gt = (GitTree) gitObjectsIndex.get(gc.getTreeId());
			gt.setDate(gc.getAutor().getDate());
			gt.addParents(gc.getParentFiles());
		}
		//puis les trees
		for (String sha1GitObject : gitObjectsIndex.getListOfAllObjectKeys()) {
			gitObject = gitObjectsIndex.get(sha1GitObject);
			
			if (!gitObject.getType().equals("tree"))
				continue;
			
			GitTree gt = (GitTree) gitObject;
			for (TreeEntry tEntry : gt.listEntry()) {
				GitObject go = gitObjectsIndex.get(tEntry.getSha1());
				go.setDate(gt.date());
				go.addName(tEntry.getName());
			}
		}
		*/
	}
	
	public void start(Stage primaryStage, String[] args) throws Exception {
		System.out.println(args[0]);
		gitObjectsIndex = indexObjects(args);
		start(primaryStage);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root;
		root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
		Scene scene = new Scene(root);


		primaryStage.setTitle("GitExplorer");
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
