package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();
			Scene scene = new Scene(root,500,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Viewer !");
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.setMaximized(true);
			ImageView imageAffiche = new ImageView(new Image(Main.class.getResourceAsStream("images/Aile de Mort VS Electromage.jpg"))); // Il faudra peut-être se servir de OutputStream pour obtenir l'image.
			root.getChildren().add(imageAffiche);
			imageAffiche.setFitWidth(scene.getWidth());
			imageAffiche.setPreserveRatio(true);
			// primaryStage.addEventHandler(primaryStage.isMaximized(), imageAffiche.setFitWidth(scene.getWidth()));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
