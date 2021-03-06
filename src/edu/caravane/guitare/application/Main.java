package edu.caravane.guitare.application;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class Main extends Application {
	protected Stage primaryStage;
	// FIXME: troisième fois qu'on trouve le même code moche.. mon dieur...
	protected static final String osBarre = System.getProperty("os.name").charAt(0) == 'W' ? "\\" : "/";

	/**
	 * This function take a path and search in each folder recursively the first
	 * .git directory and return the list of git objects in the .git/object/* as
	 * String.
	 *
	 * @author Eloan
	 *
	 * @param path
	 *            of the file to explore
	 * @return the list of absolute path of all objects
	 * @throws an
	 *             exception if there is no object
	 */
	protected String[] searchGitObject(String path) throws Exception {
		Queue<String> filePasTrie = new LinkedList<String>();
		filePasTrie.add(path);
		File[] currentListFiles;
		File currentFile = new File(path);
		File gitRepository = new File(path);
		int indexLongueur = 0;
		boolean drap = false;
		while (filePasTrie.size() != 0 && drap == false) {
			currentFile = new File(filePasTrie.element());

			if (currentFile.isDirectory()) {
				currentListFiles = currentFile.listFiles();
				for (File f : currentListFiles) {
					if (f.getAbsolutePath().contains(osBarre + ".git" + osBarre + "objects" + osBarre)) {
						indexLongueur = f.getAbsolutePath().indexOf("git" + osBarre + "objects" + osBarre)
								+ ("git" + osBarre + "objects" + osBarre).length();

						gitRepository = new File(f.getAbsolutePath().substring(0, indexLongueur));

						drap = true;
						break;
					} else if (f.isDirectory() && f.listFiles().length != 0)
						filePasTrie.add(f.toString());
				}
				filePasTrie.remove();
			}
		}
		// Exception pas levee pour une raison inconnue
		if (!gitRepository.getAbsolutePath().contains(".git")) {
			MainWindow.errorMessageBox("ERROR !! :'(", "Il n'existe pas de .git dans le repertoire");
			throw new Exception("Il n'existe pas de .git dans le repertoire");
		}

		ArrayList<String> listGitObjects = new ArrayList<String>();
		if (drap == true) {
			for (File f : gitRepository.listFiles()) {
				if (f.isDirectory() && f.listFiles().length > 1) {
					for (File ff : f.listFiles())
						listGitObjects.add(ff.toString());
				} else if (f.isDirectory() && f.listFiles().length == 1)
					listGitObjects.add(f.listFiles()[0].toString());
			}
		}

		String gitObjectArray[] = new String[listGitObjects.size()];

		for (int i = 0; i < listGitObjects.size(); ++i)
			gitObjectArray[i] = listGitObjects.get(i);

		if (gitObjectArray.length == 0)
			throw new Exception();

		return gitObjectArray;
	}

	/**
	 * Start the main window, the git objects' explorer, and hide this current
	 * drag & drop window;
	 *
	 * @author VieVie31
	 *
	 * @param path
	 * @throws an
	 *             exception if it's impossible (eg. the Eloan function crash)
	 */
	protected void startMainWindow(String path) throws Exception {
		primaryStage.hide();
		MainWindow explorerMainWindow = new MainWindow();
		explorerMainWindow.start(primaryStage, searchGitObject(path));
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		Parent root;
		AnchorPane dragPane;
		Scene scene;

		root = FXMLLoader.load(getClass().getResource("FirstScene.fxml"));
		dragPane = (AnchorPane) root.lookup("#dragPane");
		scene = new Scene(root);

		dragPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			/**
			 * When the window is clicked the user can choose the directory to
			 * scan for exploring the git objects.
			 *
			 * @author VieVie31
			 */
			@Override
			public void handle(MouseEvent event) {
				event.consume();

				File selectedDir;
				selectedDir = new DirectoryChooser().showDialog(primaryStage);

				if (selectedDir == null)
					return; // no selection ignore the event

				try {
					startMainWindow(selectedDir.getAbsolutePath());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
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
				else
					event.consume();
			}
		});

		dragPane.setOnDragDropped(new EventHandler<DragEvent>() {
			/**
			 * When something is dropped, if it's a directory we open it in the
			 * git objects' explorer. Else ignore.
			 *
			 * @author VieVie31
			 */
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if (db.hasFiles()) {
					event.setDropCompleted(true);
					event.consume();

					// on ne traite qu'un seul dossier donc on prend le premier
					File file = db.getFiles().get(0);

					// si c'est un fichier simple on ne traite pas ces merdes...
					if (file.isFile()) {
						MainWindow.errorMessageBox("ERROR !! :'(", // abrutit va
								"Draguer un repertoire, pas un ficher !!");
						return;
					} else {
						try {
							startMainWindow(file.getAbsolutePath());
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}

				} else
					event.setDropCompleted(true);

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
