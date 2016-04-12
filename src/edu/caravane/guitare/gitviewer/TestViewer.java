package edu.caravane.guitare.gitviewer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

import edu.caravane.guitare.gitobject.GitBlob;
import edu.caravane.guitare.gitobject.GitObjectReader;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class TestViewer extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root;
		root = Visionneuse.getInstance();
		Scene scene = new Scene(root);

		primaryStage.setTitle("Visionneuse");
		primaryStage.setScene(scene);
		primaryStage.show();

		// tests
		GitObjectReader gor = new GitObjectReader("/Annexes/tests/heat.flv");

		// gor.getType();
		// test avec blob
		// gor = new GitObjectReader("/Annexes/tests/test_blob.bin");
		Visionneuse.display(gor.builGitObject());

		// test avec pas blob
		// gor = new GitObjectReader("/Annexes/tests/test_commit.bin");
		// Visionneuse.display(gor.builGitObject());

		/*
		 * // Create and set the Scene. stage.setScene(scene);
		 * 
		 * // Name and display the Stage. stage.setTitle("Hello Media");
		 * stage.show();
		 * 
		 * // Create the media source. Media media = new
		 * Media("/Viewer/src/application/vid�os/Heat.mkv");
		 * 
		 * // Create the player and set to play automatically. MediaPlayer
		 * mediaPlayer = new MediaPlayer(media); mediaPlayer.setAutoPlay(true);
		 * 
		 * // Create the view and add it to the Scene. MediaView mediaView = new
		 * MediaView(mediaPlayer); ((Group)
		 * scene1.getRoot()).getChildren().add(mediaView);
		 */
	}
}