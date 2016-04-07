package edu.caravane.guitare.gitviewer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.zip.DataFormatException;

import edu.caravane.guitare.gitobejct.GitBlob;
import edu.caravane.guitare.application.Main;
import edu.caravane.guitare.gitobejct.GitObject;
import edu.caravane.guitare.gitobejct.GitObjectsIndex;


public class Visionneuse extends Parent {
	protected static Visionneuse visionneuse;
	
	protected Visionneuse() throws IOException {
		Parent p = FXMLLoader.load(getClass().getResource("Visionneuse.fxml"));
		AnchorPane visionneuseAP = (AnchorPane) p.lookup("#visionneuseAP");
		
		getChildren().add(visionneuseAP);
	}
	
	public void somethingHasChanged() throws IOException{
		Parent p1 = FXMLLoader.load(getClass().getResource("/Gitant/fxml/MainScene.fxml"));
		AnchorPane mainScene = (AnchorPane) p1.lookup("#mainScene");
		Visionneuse visionneuse = Visionneuse.getInstance();
		visionneuse.setLayoutX(mainScene.getLayoutX());
		visionneuse.setLayoutY(mainScene.getLayoutY());
	}
	
	public static Visionneuse getInstance() throws IOException {
		if (Visionneuse.visionneuse == null){
			Visionneuse.visionneuse = new Visionneuse();
		}
		return Visionneuse.visionneuse;
	}
	
	public static void display(String hash) throws IOException, DataFormatException {
		GitObject gitObject = GitObjectsIndex.getInstance().get(hash);
		display(gitObject);
	}

	public static void display(GitObject gitObject) throws IOException, DataFormatException {
		Visionneuse visionneuse = Visionneuse.getInstance();
		TextArea textArea = (TextArea) visionneuse.lookup("#visionneuseT");
		ImageView imageView = (ImageView) visionneuse.lookup("#visionneuseI");
		Label hashLabel = (Label) visionneuse.lookup("#hashLabel");
		
		//actualiser le label
		hashLabel.setText("Hash : " + gitObject.getId());
		
		//Test, savoir si c'est du texte, une vidéo, une image ou du son.
		//System.out.println();
		//Si c'est du texte
		//if ( == "txt"){
			
		//}
		//Actualiser la visionneuse texte
		if (gitObject.getType().equals("blob")){
			String nomObjet = gitObject.nameProperty().getValue();
			if (nomObjet.contains(".bmp") || nomObjet.contains(".gif") || nomObjet.contains(".jpeg") || nomObjet.contains(".png")){
				//Image image = new Image(gitObject.getParentFiles()[0]);
				//System.out.println(gitObject.getParentFiles()[0]);
				//imageView.setImage(image);
				//imageView.setImage(image);
			}//else{
				
			//} 
			else {
				textArea.setText(new String(((GitBlob) gitObject).getData()));
			}
		}else{
			textArea.setText(gitObject.toString());
		}
		//Si c'est une vidéo
//		if (gitObject.getType().equals("blob"))
//			mediaPlayer.(new String(((GitBlob) gitObject).getData()));
//		else
//			textArea.setText(gitObject.toString());
	}
}
