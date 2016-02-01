package edu.caravane.guitare.application;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.application.Application;


public class Main extends Application {
	protected Stage primaryStage;
	protected final String osBarre = 
			System.getProperty("os.name").charAt(0) == 'W' ? "\\" : "/" ;

	/**
	 * This function take a path and search in each folder
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
		Queue<String> filePasTrie = new LinkedList<String>();
		filePasTrie.add(path);
		File[] cur;
		File current = new File(path);
		File git = new File(path);
		int indexLongueur = 0;
		boolean drap = false;
		while (filePasTrie.size() != 0 && drap == false) {
			current = new File(filePasTrie.element());
			
			if (current.isDirectory()) {
				cur = current.listFiles();
				for (File f : cur) {
					if (f.getAbsolutePath().contains(
							osBarre + ".git" + osBarre + "objects" + osBarre)) {
						indexLongueur = f.getAbsolutePath().indexOf(
								"git" + osBarre + "objects" + osBarre) 
								+("git" + osBarre + "objects" + osBarre).length();

						git = new File(f.getAbsolutePath().
								substring(0, indexLongueur));
						
						drap = true;
						break;
					} else if (f.isDirectory() && f.listFiles().length != 0) 
						filePasTrie.add(f.toString());	
				}
				filePasTrie.remove();
			}
		}
		// Exception pas levee pour une raison inconnue
		if (!git.getAbsolutePath().contains(".git"))
			throw new Exception("Il n'existe pas de .git dans le repertoire");
		
		
		
		ArrayList<String> listg = new ArrayList<String>();
		if (drap == true){
			for (File f : git.listFiles()) {
				if (f.isDirectory() && f.listFiles().length > 1) {
					for (File ff : f.listFiles()) 
						listg.add(ff.toString());
				} else if(f.isDirectory() && f.listFiles().length == 1) 
					listg.add(f.listFiles()[0].toString());	
			}
		}
		
		String listgit[] = new String[listg.size()];
		
		for(int i =0;i<listg.size();i++) 
			listgit[i] = listg.get(i);
		
		if(listgit.length == 0)
			throw new Exception();
		
		return listgit;
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
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
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
