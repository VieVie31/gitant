package edu.caravane.guitare.application;

import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.*;

import java.util.ArrayList;

import edu.caravane.guitare.gitobejct.GitCommit;
import edu.caravane.guitare.gitobejct.GitObject;
import edu.caravane.guitare.gitobejct.GitObjectReader;
import edu.caravane.guitare.gitobejct.GitObjectsIndex;
import edu.caravane.guitare.gitobejct.GitTag;
import edu.caravane.guitare.gitobejct.GitTree;
import edu.caravane.guitare.gitobejct.TreeEntry;
import javafx.application.Application;

public class MainWindow extends Application {
	protected final String osBarre = 
			System.getProperty("os.name").charAt(0) == 'W' ? "\\" : "/" ;
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
				if (pathObj.contains(osBarre+"pack"+osBarre))
					continue;

				gor = new GitObjectReader(pathObj);
				goi.put(gor.getId(), gor.builGitObject());
			}

			return goi;
	}


	public void makeLinks(){
		GitObjectsIndex goi = GitObjectsIndex.getInstance();
		ArrayList<String> sha1Keys = goi.getListOfAllObjectKeys();
		for(String p : sha1Keys){
			if(goi.get(p).getType().equals("tag")){
				GitTag tag = (GitTag) goi.get(p);
				//On récupère l'objet tagger
				GitObject tagger = goi.get(tag.getObjHexId());
				//On ajoute son parent ( le taggeur ) 
				tagger.addParent(tag.getId());
				
			}
			/*else if(goi.get(p).getType().equals("commit")){
				
			}*/
				
			
		}
	}

	public void start(Stage primaryStage, String[] args) throws Exception {
		gitObjectsIndex = indexObjects(args);
		indexObjects(args);
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
