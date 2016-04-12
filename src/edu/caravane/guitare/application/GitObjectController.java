package edu.caravane.guitare.application;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import edu.caravane.guitare.gitobject.GitObject;
import edu.caravane.guitare.gitobject.GitObjectsIndex;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * This class add Git Object in the table view and allows to make search in the
 * table.
 *
 * @author Marvyn
 */
public class GitObjectController {

	FilteredList<GitObject> filteredData;

	FilteredList<GitObject> base;

	@FXML
	private TextField searchEntry;
	@FXML
	private TableView<GitObject> objectTable;
	@FXML
	private TableColumn<GitObject, String> SHA1;
	@FXML
	private TableColumn<GitObject, String> Type;
	@FXML
	private TableColumn<GitObject, String> Nom;
	@FXML
	private TableColumn<GitObject, String> Size;

	private ObservableList<GitObject> masterData = FXCollections
			.observableArrayList();

	public GitObjectController() {
		GitObjectsIndex goi = GitObjectsIndex.getInstance();
		for (String key : goi.getListOfAllObjectKeys()) {
			if (goi.get(key).getNames().length == 0)
				goi.get(key).addName("PAS_DE_NOM");
			masterData.add(goi.get(key));
		}

	}

	@FXML
	private void initialize() {
		SHA1.setCellValueFactory(cellData -> cellData.getValue().sha1Property());
		Type.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
		Nom.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		Size.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());

		filteredData = new FilteredList<>(masterData, p -> true);
		base = new FilteredList<>(masterData, p -> true);

		searchEntry.addEventFilter(KeyEvent.KEY_PRESSED,
				new EventHandler<KeyEvent>() {

					@Override
					public void handle(KeyEvent event) {
						// TODO Auto-generated method stub
						objectTable.setItems(base);
						if (event.getCode() == KeyCode.ENTER) {
							SortedList<GitObject> sortedData = new SortedList<>(
									filteredData);
							objectTable.setItems(sortedData);
						}

						searchEntry
								.textProperty()
								.addListener(
										(observable, oldValue, newValue) -> {
											filteredData.setPredicate(person -> {
												// If filter text is empty,
												// display objects.
												if (newValue == null
														|| newValue.isEmpty()) {
													return true;
												}

												String lowerCaseFilter = newValue
														.toLowerCase();

												if (lowerCaseFilter
														.contains(":")) {
													String col = lowerCaseFilter
															.substring(
																	0,
																	lowerCaseFilter
																			.indexOf(":"));
													String search = lowerCaseFilter.substring(
															lowerCaseFilter
																	.indexOf(":") + 1,
															lowerCaseFilter
																	.length());

													if (person.getId()
															.toLowerCase()
															.contains(search)
															&& (col.equals("id") || col
																	.equals("sha1"))) {
														return true; // Filter
																		// matches
																		// sha1.
													} else if (person.getType()
															.toString()
															.toLowerCase()
															.contains(search)
															&& col.equals("type")) {
														return true; // Filter
																		// matches
																		// type.
													} else if (person
															.getNames()[0]
															.toLowerCase()
															.contains(search)
															&& col.equals("nom")) {
														return true; // Filter
																		// matches
																		// name.
													} else if (Integer
															.toString(
																	person.getSize())
															.toLowerCase()
															.contains(search)
															&& col.equals("size")) {
														return true; // Filter
																		// matches
																		// size.
													}

													return false; // Does not
																	// match.
												} else {
													if (person
															.getId()
															.toLowerCase()
															.contains(
																	lowerCaseFilter)) {
														return true; // Filter
																		// matches
																		// sha1.
													} else if (person
															.getType()
															.toString()
															.toLowerCase()
															.contains(
																	lowerCaseFilter)) {
														return true; // Filter
																		// matches
																		// type.
													} else if (person
															.getNames()[0]
															.toLowerCase()
															.contains(
																	lowerCaseFilter)) {
														return true; // Filter
																		// matches
																		// name.
													} else if (Integer
															.toString(
																	person.getSize())
															.toLowerCase()
															.contains(
																	lowerCaseFilter)) {
														return true; // Filter
																		// matches
																		// size.
													}

													return false; // Does not
																	// match.
												}

											});
										});

					}

				});

		objectTable.setItems(base);
	}
}