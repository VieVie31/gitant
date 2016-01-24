package edu.caravane.guitare.application;

import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.application.Application;

public class MainWindow extends Application {

	public void start(Stage primaryStage, String[] args) throws Exception {
		System.out.println(args[0]);
		start(primaryStage);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root;
		root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));  ;
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("GitExplorer");
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
