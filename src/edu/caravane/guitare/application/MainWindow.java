package edu.caravane.guitare.application;

import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.*;
import edu.caravane.guitare.gitobejct.GitObjectReader;
import edu.caravane.guitare.gitobejct.GitObjectsIndex;
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

			System.out.println("bla");
			
			for (String pathObj : listObjs) {
				//on ne traite pas les pack pour le moment
				if (pathObj.contains("/pack/"))
					continue;
				
				gor = new GitObjectReader(pathObj);
				goi.put(gor.getId(), gor.builGitObject());
			}
			
			System.out.println("coucou");
			
			return goi;
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
