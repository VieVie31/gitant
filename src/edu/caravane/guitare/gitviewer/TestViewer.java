package edu.caravane.guitare.gitviewer;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;


public class TestViewer extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Viewer !");
			primaryStage.setScene(scene);
			ImageView imageAffiche = new ImageView(new Image(TestViewer.class.getResourceAsStream("images/Aile de Mort VS Electromage.jpg")));
			root.getChildren().add(imageAffiche);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
