package edu.caravane.guitare.application;

import java.io.File;

import javax.xml.stream.events.StartDocument;

import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.scene.text.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.application.Application;

//12h01 -> 12h43
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
