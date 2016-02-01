package edu.caravane.guitare.application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import edu.caravane.guitare.test.GitTest;

/**
 * This class add Git Object in the table view and allows to make search 
 * in the table.
 *
 * @author Marvyn
 */
public class GitObjectController {
	
	FilteredList<GitTest> filteredData;

	@FXML
	private TextField searchEntry;
	@FXML
	private TableView<GitTest> objectTable;
	@FXML
	private TableColumn<GitTest,String> SHA1;
	@FXML
	private TableColumn<GitTest, String> Type;
	@FXML
	private TableColumn<GitTest,String> Nom;
	@FXML
	private TableColumn<GitTest, String> Size;
	@FXML
	private TableColumn<GitTest,String> Date;


	private ObservableList<GitTest> masterData = FXCollections.observableArrayList();
	
	public GitObjectController() {
		//Juste un test pour voir si cela fonctionne
        masterData.add(new GitTest("1fd15f4d84", "Blob","Essai.txt","1 ko","12/10/12"));
        masterData.add(new GitTest("14gr51e6rg", "Blob","Essai4.txt","17 ko","36/10/12"));
        masterData.add(new GitTest("ve5frv4e64", "Blob","yolo.pdf","80 ko","12/10/12"));
        masterData.add(new GitTest("verfv4d516", "Blob","titi.vlc","66 ko","26/10/12"));
        masterData.add(new GitTest("th78745248", "Tree","th78745248","48 ko","20/10/12"));
        masterData.add(new GitTest("verv798452", "Tree","verv798452","5 ko","19/10/12"));
        masterData.add(new GitTest("jeudezf845", "Tree","jeudezf845","745 ko","15/10/12"));
        masterData.add(new GitTest("ver8b45121", "Tag","app","457 ko","8/10/12"));
        masterData.add(new GitTest("s4fge5v128", "Blob","herisson.txt","965 ko","14/10/12"));
        masterData.add(new GitTest("4ve6rv4ed5", "Commit","4ve6rv4ed5","4984465 ko","8/10/12"));
        masterData.add(new GitTest("4v454r1e6v", "Commit","4v454r1e6v","7845 ko","6/10/12"));
        masterData.add(new GitTest("8797879879", "Tag","E","78797 ko","1/01/12"));
    }
	
	@FXML
    private void initialize() {
        SHA1.setCellValueFactory(cellData -> cellData.getValue().sha1Property());
        Type.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        Nom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        Size.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        Date.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        filteredData = new FilteredList<>(masterData, p -> true);


        searchEntry.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display objects.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getSha1().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches sha1.
                } else if (person.getType().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches type.
                }else if (person.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches name.
                }else if (person.getSize().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches size.
                }else if (person.getDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches date.
                }
                
                return false; // Does not match.
            });
        });
        
        SortedList<GitTest> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(objectTable.comparatorProperty());

        objectTable.setItems(sortedData);
    }
}
	

