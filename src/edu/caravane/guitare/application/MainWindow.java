package edu.caravane.guitare.application;

import javafx.fxml.*;
import javafx.stage.*;
import sun.security.jca.GetInstance;
import javafx.scene.*;
import edu.caravane.guitare.gitobejct.GitObjectReader;
import edu.caravane.guitare.gitobejct.GitObjectsIndex;
import javafx.application.Application;

public class MainWindow extends Application {

	/**
	 * this function add all the objects into a GitObjectsIndex object
	 *
	 * @author Sylvain
	 *
	 * @param listObjs
	 * @return
	 * @throws Exception
	 */
	public GitObjectsIndex indexObjects(String[] listObjs) throws Exception {
			GitObjectsIndex goi =  GitObjectsIndex.getInstance();
			GitObjectReader gor;

			for (String pathObj : listObjs) {
				gor = new GitObjectReader(pathObj);
				goi.put(gor.getId(), gor.builGitObject());
			}

			return goi;
	}

	public void start(Stage primaryStage, String[] args) throws Exception {
		System.out.println(args[0]);
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
