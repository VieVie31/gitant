package edu.caravane.guitare.gitviewer;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.util.zip.DataFormatException;

import edu.caravane.guitare.gitobejct.GitBlob;
import edu.caravane.guitare.gitobejct.GitObject;
import edu.caravane.guitare.gitobejct.GitObjectsIndex;


public class Visionneuse extends Parent {
	protected static Visionneuse visionneuse;
	protected static AnchorPane visionneuseAP;
	protected static Node displayedNode;
	
	protected Visionneuse() throws IOException {
		//Parent p = FXMLLoader.load(getClass().getResource("Visionneuse.fxml"));
		//visionneuseAP = (AnchorPane) p.lookup("#visionneuseAP");
	}
	
	public void setAp(AnchorPane visionneuseAP) {
        this.visionneuseAP = visionneuseAP;
    }
   
    public static double getWidth() {
        return visionneuseAP.getWidth();
    }
   
    public static double getHeight() {
        return visionneuseAP.getHeight();
    }
    
    public static void setWidth(double width) {
        visionneuseAP.widthProperty().subtract(visionneuseAP.getWidth()).add(width);
    }

    public static void setHeight(double height) {
        visionneuseAP.heightProperty().subtract(visionneuseAP.getHeight()).add(height);
    }

    public static void rebound(String hash) throws IOException, DataFormatException {
        if (displayedNode == null)
            return;
        if (displayedNode instanceof TextArea){
            display(hash);
        }
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
		visionneuse.getChildren().clear();
		
		Visionneuse visionneuse = Visionneuse.getInstance();
		
		TextArea textArea = new TextArea();
		textArea.setMinWidth(getWidth());
        textArea.setMinHeight(getHeight());
        textArea.setMaxWidth(getWidth());
        textArea.setMaxHeight(getHeight());
        textArea.setEditable(false);
        
        Label hashLabel = new Label("Hash : " + gitObject.getId());
		
		Tab infos = new Tab("File infos");
		Tab textView = new Tab("Tex view");
		Tab imageView = new Tab("Image view");
		
		TabPane tabPane = new TabPane(infos, textView, imageView);
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		infos.setContent(hashLabel);
		textView.setContent(textArea);
		
		tabPane.minWidth(getWidth());
		tabPane.minHeight(getHeight());
		tabPane.maxWidth(getWidth());
        tabPane.maxHeight(getHeight());
		
		/*
		TextArea textArea = (TextArea) visionneuse.lookup("#visionneuseT");
		//MediaPlayer mediaPlayer = (MediaPlayer) visionneuse.lookup("#visionneuseTV");
		Label hashLabel = (Label) visionneuse.lookup("#hashLabel");
		
		//actualiser le label
		hashLabel.setText();
		
		//Test, savoir si c'est du texte, une vid�o, une image ou du son.
		
		//Si c'est du texte
		//Actualiser la visionneuse texte*/
		if (gitObject.getType().equals("blob"))
			textArea.setText(new String(((GitBlob) gitObject).getData()));
		else
			textArea.setText(gitObject.toString());
		
        /*visionneuse.getChildren().clear();

        textArea.setEditable(false);

        visionneuse.minWidth(getWidth());
        visionneuse.minHeight(getHeight());
        visionneuse.maxWidth(getWidth());
        visionneuse.maxHeight(getHeight());*/

        displayedNode = tabPane;

        visionneuse.getChildren().add(displayedNode);
	}
}
