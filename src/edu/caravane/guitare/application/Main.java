package edu.caravane.guitare.application;

import java.io.File;

import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.application.Application;


public class Main extends Application {
	protected Stage primaryStage;

	/**
	 * This function take a path ans search in each folder
	 * recursively the first .git directory and return the list
	 * of git objects in the  .git/object/* as String.
	 * 
	 * @author Eloan
	 *
	 * @param  path of the file to explore
	 * @return the list of absolute path of all objects
	 * @throws an exception if there is no object
	 */
	protected String[] searchGitObject(String path) throws Exception {
		// code temporaire avant qu'eloan ne fasse la fonction
		String[] tmp = new String[1];
		tmp[0] = path;
		return tmp; //la fonction ne devra jamais renvoyer null !!
	}

	/**
	 * Start the main window, the git objects' explorer, and hide this
	 * current drag & drop window;
	 * 
	 * @author VieVie31
	 *
	 * @param  path
	 * @throws an exception if it's impossible (eg. the Eloan function crash)
	 */
	protected void startMainWindow(String path) throws Exception {
		primaryStage.hide();
		MainWindow explorerMainWindow = new MainWindow();
		explorerMainWindow.start(primaryStage, searchGitObject(path));
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		/*// appliquer du css avec :
		 * scene.getStylesheets().
		 * add(getClass().
		 * 		getResource("application.css").
		 * 		toExternalForm());
		 */
		this.primaryStage = primaryStage;

		Parent root;
		AnchorPane dragPane;
		Scene scene;

		root = FXMLLoader.load(getClass().getResource("FirstScene.fxml"));
		dragPane = (AnchorPane) root.lookup("#dragPane");
		scene = new Scene(root);

		dragPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			/**
			 * When the window is clicked the user can choose the directory
			 * to scan for exploring the git objects.
			 * 
			 * @author VieVie31
			 */
			@Override
			public void handle(MouseEvent event) {
				event.consume();

				File selectedDir;
				selectedDir = new DirectoryChooser().showDialog(primaryStage);

				if (selectedDir == null) return; //no selection ignore the event

				try {
					startMainWindow(selectedDir.getAbsolutePath());
				} catch (Exception e) {}
			}
		});

		dragPane.setOnDragOver(new EventHandler<DragEvent>() {
			/**
			 * Enable the drag and drop function for files transfert.
			 * 
			 * @author VieVie31
			 */
			@Override
			public void handle(DragEvent event) {
				if (event.getDragboard().hasFiles()) 
					event.acceptTransferModes(TransferMode.COPY);
				else event.consume();
			}
		});

		dragPane.setOnDragDropped(new EventHandler<DragEvent>() {
			/**
			 * When something is dropped, if it's a directory we open it in
			 * the git objects' explorer. Else ignore.
			 * 
			 * @author VieVie31
			 */
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if (db.hasFiles()) {
					event.setDropCompleted(true);
					event.consume();

					//on ne traite qu'un seul dossier donc on prend le premier
					File file = db.getFiles().get(0);

					//si c'est un fichier simple on ne traite pas ces merdes...
					if (file.isFile()) return; //faire une popup d'erreur ?
					else {
						try {
							startMainWindow(file.getAbsolutePath());
						} catch (Exception e) {}
					}

				} else event.setDropCompleted(true);

			}
		});

		primaryStage.setTitle("Drag Your Folder Here");
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
