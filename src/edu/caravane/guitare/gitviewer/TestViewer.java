package edu.caravane.guitare.gitviewer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

import edu.caravane.guitare.gitobejct.GitBlob;
import edu.caravane.guitare.gitobejct.GitObjectReader;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class TestViewer extends Application { 
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root;
		root = Visionneuse.getInstance();
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("Visionneuse");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//tests
		GitObjectReader gor;
		//test avec blob
		gor = new GitObjectReader("Annexes/tests/test_blob.bin");
		Visionneuse.display(gor.builGitObject());
		//test avec pas blob
		gor = new GitObjectReader("Annexes/tests/test_commit.bin");
		Visionneuse.display(gor.builGitObject());
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}