package edu.caravane.guitare.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.DataFormatException;

import org.eclipse.jgit.internal.storage.file.PackFile;
import org.eclipse.jgit.internal.storage.file.PackIndex.MutableEntry;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

import edu.caravane.guitare.gitobject.GitBlob;
import edu.caravane.guitare.gitobject.GitCommit;
import edu.caravane.guitare.gitobject.GitObject;
import edu.caravane.guitare.gitobject.GitObjectReader;
import edu.caravane.guitare.gitobject.GitObjectType;
import edu.caravane.guitare.gitobject.GitObjectsIndex;
import edu.caravane.guitare.gitobject.GitTag;
import edu.caravane.guitare.gitobject.GitTree;
import edu.caravane.guitare.gitobject.TreeEntry;
import edu.caravane.guitare.gitviewer.Visionneuse;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainWindow extends Application {
	// FIXME: mais que c'est moche...
	protected static final String osBarre = System.getProperty("os.name").charAt(0) == 'W' ? "\\" : "/";
	protected GitObjectsIndex gitObjectsIndex;

	// javafx objects used
	@SuppressWarnings("rawtypes")
	// oui on est des branleurs !! et alors ?
	protected TableView objectTable;
	protected AnchorPane visionneuseAP; // integration de la visionneuse
	protected TextField searchEntry; // la barre de recherche
	protected ListView<String> listParents;
	protected SplitPane splitPane;
	protected SplitPane splitPane2;
	protected AnchorPane mainScene;

	/**
	 * This function pop-up an error message box with the title and error
	 * message given in parameters
	 *
	 * @author VieVie31
	 *
	 * @param titre
	 *            of the error box
	 * @param message
	 *            of the error box
	 */
	public static void errorMessageBox(String titre, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titre);
		alert.setContentText(message); // alert.setHeaderText("");
		alert.showAndWait();
	}

	/**
	 * This function add all the objects into a GitObjectsIndex object
	 *
	 * @author Sylvain, VieVie31
	 *
	 * @param listObjs
	 *            a list of paths (String) of git object
	 * @return the GitObjectsIndex containing all the objects by sha1
	 * @throws Exception
	 *             if something wrong appends
	 */
	public GitObjectsIndex indexObjects(String[] listObjs) throws Exception {
		GitObjectReader gor;
		GitObjectsIndex goi = GitObjectsIndex.getInstance();
		for (String pathObj : listObjs) {
			if (pathObj.contains(osBarre + "pack" + osBarre))
				if (pathObj.contains(osBarre + "pack" + osBarre)) {
					makePack(pathObj);
					continue;
				}
			gor = new GitObjectReader(pathObj);
			goi.put(gor.getId(), gor.builGitObject());
		}
		return goi;
	}

	/**
	 * Function used to treat pack object, it load the data from each object of
	 * the pack to create the original object when the object is recreate it
	 * store the object into the git object index
	 *
	 * @author Sylvain
	 *
	 * @param pathPack
	 *            the path of the pack file
	 * @throws IOException
	 * @throws DataFormatException
	 */
	public static void makePack(String pathPack) throws IOException, DataFormatException {
		String[] path = pathPack.split(osBarre + ".git" + osBarre);
		RepositoryBuilder builder = new RepositoryBuilder();
		builder.setMustExist(true);
		builder.setGitDir(new File(path[0] + osBarre + ".git" + osBarre));
		Repository repository = builder.build();

		for (MutableEntry mutableEntry : new PackFile(new File(pathPack), 0)) {
			GitObject obj = null;
			String sha1 = mutableEntry.toObjectId().name();
			ObjectId id = repository.resolve(sha1);
			ObjectLoader loader = repository.open(id);

			switch (loader.getType()) {
			case Constants.OBJ_BLOB:
				obj = new GitBlob(loader.getSize(), sha1, "", loader.getCachedBytes());
				break;
			case Constants.OBJ_COMMIT:
				obj = new GitCommit(loader.getSize(), sha1, new String(loader.getCachedBytes()));
				break;
			case Constants.OBJ_TREE:
				obj = new GitTree(loader.getSize(), sha1, loader.getBytes());
				break;
			case Constants.OBJ_TAG:
				obj = new GitTag(loader.getSize(), sha1, new String(loader.getCachedBytes()));
				break;
			default:
				break;
			}

			GitObjectsIndex.getInstance().put(mutableEntry.toObjectId().name(), obj);
		}
	}

	/**
	 * This function browse in a tree the find all his son and add son's name
	 * and parents
	 *
	 * @author Marvyn, VieVie31
	 *
	 * @param tree
	 */
	protected void parcoursTree(GitTree tree) {
		GitObjectsIndex goi = GitObjectsIndex.getInstance();
		ArrayList<String> keys = goi.getListOfAllObjectKeys();
		for (TreeEntry treeEntry : tree.listEntry()) {
			if (keys.contains(treeEntry.getSha1())) {
				if (GitObjectType.BLOB.equals(goi.get(treeEntry.getSha1()).getType())) {
					// Si c'est un blob, on lui donne son nom et le parent
					GitBlob blob = (GitBlob) goi.get(treeEntry.getSha1());

					if (!Arrays.asList(blob.getParentFiles()).contains(treeEntry.getName()))
						blob.addName(treeEntry.getName());

					if (!Arrays.asList(blob.getParentFiles()).contains(tree.getId()))
						blob.addParent(tree.getId());

				} else if (GitObjectType.TREE.equals(goi.get(treeEntry.getSha1()).getType())) {
					// On regarde si c'est un arbre different de lui-meme pour
					// ne pas boucler a l'infini
					if (!tree.getId().equals(treeEntry.getSha1())) {
						// On ajoute ces parents et on le parcours
						GitTree treeSon = (GitTree) goi.get(treeEntry.getSha1());

						if (!Arrays.asList(treeSon.getParentFiles()).contains(treeEntry.getName()))
							treeSon.addName(treeEntry.getName());

						if (!Arrays.asList(treeSon.getParentFiles()).contains(tree.getId()))
							treeSon.addParent(tree.getId());
						parcoursTree(treeSon); // Recursivite powa
					}
				}
			}
		}
	}

	/**
	 * This function find parents of tag, commit, tree and blob. It add too the
	 * blob's name.
	 *
	 * @author Marvyn
	 *
	 * @throws Exception
	 */
	protected void makeLinks() throws Exception {
		GitObjectsIndex goi = GitObjectsIndex.getInstance();
		ArrayList<String> sha1Keys = goi.getListOfAllObjectKeys();
		for (String p : sha1Keys) {
			if (GitObjectType.TAG.equals(goi.get(p).getType())) {
				GitTag tag = (GitTag) goi.get(p);
				// On recupere l'objet tagger
				GitObject taggay = goi.get(tag.getObjHexId());
				// On ajoute son parent ( le taggeur )
				taggay.addParent(tag.getId());
			} else if (GitObjectType.COMMIT.equals(goi.get(p).getType())) {
				GitCommit commit = (GitCommit) goi.get(p);
				commit.addName(commit.getId());

				// En attendant que l'on trouve mieux.
				for (int i = 0; i < commit.getParentListId().size(); i++)
					commit.addParent(commit.getParentListId().get(i));

				// On recupere le tree du commit
				GitTree treeCommit = (GitTree) goi.get(commit.getTreeId());
				// On ajoute le parent du tree
				treeCommit.addParent(commit.getId());
				// On appelle la fonction recursive qui parcours l'arbre
				parcoursTree(treeCommit);

			}
		}
	}

	/**
	 * This function display all the info of a git object selected by his hash
	 * int the viewer and in the list of the parents...
	 *
	 * @author VieVie31
	 *
	 * @param hash
	 */
	protected void displayAllInfo(String hash) {
		// pour afficher la liste des parents
		try {
			ObservableList<String> hashParentsList;
			hashParentsList = FXCollections.observableArrayList();

			for (String s : GitObjectsIndex.getInstance().get(hash).getParentFiles())
				hashParentsList.add(s);

			listParents.setItems(hashParentsList); // l'afficher dans la
													// ListView...
		} catch (Exception e) {
			errorMessageBox("ERROR DISPLAY", "Can't display the parent(s) hash :".concat(hash));
		}

		// pour afficher dans la visionneuse
		try {
			Visionneuse.display(hash);
		} catch (Exception e) {
			errorMessageBox("ERROR DISPLAY", "Can't display the object :".concat(hash));
		}
	}

	/**
	 * This function return the column of a TableView with the name specified in
	 * parameter.
	 *
	 * @author VieVie31
	 *
	 * @param tableView
	 *            where is the column to get
	 * @param name
	 *            of the columns to return
	 * @return the column if found null else
	 */
	private <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
		for (TableColumn<T, ?> column : tableView.getColumns())
			if (column.getText().equals(name))
				return column;

		return null;
	}

	public void start(Stage primaryStage, String[] args) throws Exception {
		gitObjectsIndex = indexObjects(args);
		indexObjects(args);
		makeLinks();
		start(primaryStage);
	}

	/**
	 * Main interface loaded here...
	 *
	 * @author Eloan, VieVie31
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root;
		root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setTitle("GitExplorer");
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(400);
		primaryStage.setScene(scene);
		primaryStage.show();

		visionneuseAP = (AnchorPane) root.lookup("#gitObjectViewerSpace");
		visionneuseAP.getChildren().add(Visionneuse.getInstance());
		Visionneuse.getInstance().setAp(visionneuseAP);
		objectTable = (TableView) root.lookup("#objectTable");
		searchEntry = (TextField) root.lookup("#searchEntry"); // la barre de
																// recherche
		listParents = (ListView<String>) root.lookup("#listParents");
		splitPane = (SplitPane) root.lookup("#splitPane");
		mainScene = (AnchorPane) root.lookup("#mainScene");
		splitPane2 = (SplitPane) root.lookup("#splitPane2");

		// la liste des parents du fichier recherche...
		listParents.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() < 2)
					return;

				String hash = listParents.getItems().get(listParents.getSelectionModel().getSelectedIndex());

				displayAllInfo(hash);
			}

		});

		objectTable.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			/**
			 * This function associate the action of the double click on a cell
			 * int the TableView to display his parents hash and display it in
			 * the viewer... If the display or the parents listing throws an
			 * exception, the exception will be catched but an error dialog
			 * message will pop-up
			 *
			 * @author VieVie31, Eloan
			 */
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) { // double clicked
					// recuperer les hash en fonction de la ou on a
					// clicke
					// en tenant compte du fait que les colomnes aient
					// pu permuter...
					String hash = (String) getTableColumnByName(objectTable, "SHA-1")
							.getCellData(objectTable.getSelectionModel().getSelectedIndex());

					displayAllInfo(hash);
				}
			}
		});

		/**
		 * This listener is used to call the resize function of the visionneuse
		 * when the AnchorPane of the visionneuse is changed.
		 *
		 * @author TheHaricover
		 */
		visionneuseAP.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
					Number newSceneHeight) {
				// Visionneuse.setWidth(primaryStage.getWidth());
				String hash = (String) getTableColumnByName(objectTable, "SHA-1")
						.getCellData(objectTable.getSelectionModel().getSelectedIndex());
				try {
					Visionneuse.resize(hash);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		/**
		 * This listener is used to call the resize function of the visionneuse
		 * when the AnchorPane of the visionneuse is changed.
		 *
		 * @author TheHaricover
		 */
		visionneuseAP.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
					Number newSceneHeight) {
				// Visionneuse.setWidth(primaryStage.getWidth());
				String hash = (String) getTableColumnByName(objectTable, "SHA-1")
						.getCellData(objectTable.getSelectionModel().getSelectedIndex());
				try {
					Visionneuse.resize(hash);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
