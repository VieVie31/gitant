package edu.caravane.guitare.application;

import java.io.File;

import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.scene.text.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.application.Application;


public class Main extends Application {
	@Override
	public void start(final Stage primaryStage) {
		try {
			//start 18h40 -> 20h25
			//      21h19 -> 21h46
			//temporaire... structure a changer... example de codes pour drang and drop et tout...
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Parent root = FXMLLoader.load(getClass().getResource("FirstScene.fxml"));

			Scene scene = new Scene(root);

			AnchorPane dragPane = (AnchorPane) root.lookup("#dragPane");
			
			dragPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					DirectoryChooser directoryChooser = new DirectoryChooser();
					File selectedDir = directoryChooser.showDialog(primaryStage);
					if (selectedDir == null)
						return;
					//un dossier a ete selectionne... on l'ouvre...
					System.out.println(selectedDir.getAbsolutePath());
					
					event.consume();
				}
			});
			
			dragPane.setOnDragOver(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					if (event.getDragboard().hasFiles()) 
						event.acceptTransferModes(TransferMode.COPY);
					else event.consume();
				}
			});

			dragPane.setOnDragDropped(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					Dragboard db = event.getDragboard();
					boolean success = false;
					if (db.hasFiles()) {
						success = true;
						String filePath = null;
						//on ne traite qu'un seul dossier
						File file = db.getFiles().get(0); //donc on prend le premier
						filePath = file.getAbsolutePath();

						System.out.println(filePath);

						try {
							if (file.isFile()) {
								//c'est un fichier simple on traiter pas ces merdes...
							} else {
								//c'est un dossier
								//on cherche le git
								//puis on l'affiche
								primaryStage.setTitle("coucou les suckers");
								primaryStage.setScene(
										new Scene((Parent) FXMLLoader.
												load(getClass().
												getResource("MainScene.fxml"))));
								primaryStage.show();
							}
							
						} catch (Exception e) {}

					}
					event.setDropCompleted(success);
					event.consume();
				}
			});

			primaryStage.setTitle("Drag Your Folder Here");
			primaryStage.setMinWidth(600);
			primaryStage.setMinHeight(400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
