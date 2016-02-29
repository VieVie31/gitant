package edu.caravane.guitare.application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


import edu.caravane.guitare.gitobejct.GitObject;
import edu.caravane.guitare.gitobejct.GitObjectsIndex;

/**
 * This class add Git Object in the table view and allows to make search 
 * in the table.
 *
 * @author Marvyn
 */
public class GitObjectController {
		
	FilteredList<GitObject> filteredData;

	@FXML
	private TextField searchEntry;
	@FXML
	private TableView<GitObject> objectTable;
	@FXML
	private TableColumn<GitObject,String> SHA1;
	@FXML
	private TableColumn<GitObject, String> Type;
	@FXML
	private TableColumn<GitObject,String> Nom;
	@FXML
	private TableColumn<GitObject, String> Size;



	private ObservableList<GitObject> masterData = FXCollections.observableArrayList();
	
	public GitObjectController() {
		GitObjectsIndex goi =  GitObjectsIndex.getInstance();
		for(String key : goi.getListOfAllObjectKeys()){
			System.out.println(goi.get(key).sizeProperty());
			if(goi.get(key).getNames().length==0)
				goi.get(key).addName("temp");
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


        searchEntry.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display objects.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches sha1.
                } else if (person.getType().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches type.
                }else if (person.getNames()[0].toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches name.
                }else if (Integer.toString(person.getSize()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches size.
                }
                
                return false; // Does not match.
            });
        });
        
        SortedList<GitObject> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(objectTable.comparatorProperty());

        objectTable.setItems(sortedData);
    }
}