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
import edu.caravane.guitare.gitobejct.GitCommit;
import edu.caravane.guitare.application.Main;
import edu.caravane.guitare.gitobejct.GitObject;
import edu.caravane.guitare.gitobejct.GitObjectType;
import edu.caravane.guitare.gitobejct.GitObjectsIndex;
import edu.caravane.guitare.gitobejct.GitTag;
import edu.caravane.guitare.gitobejct.GitTree;


public class Visionneuse extends Parent {
	protected static Visionneuse visionneuse;
	protected static AnchorPane visionneuseAP;
	protected static Node displayedNode;
	
	protected Visionneuse() throws IOException {
		
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
	
	public static void autobound() {
		if (displayedNode == null)
			return;

		displayedNode.minWidth(getWidth());
		displayedNode.minHeight(getHeight());
		displayedNode.maxWidth(getWidth());
		displayedNode.maxHeight(getHeight());
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
		visionneuse.getChildren().clear();
		
		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		
		textArea.setMinWidth(getWidth());
		textArea.setMinHeight(getHeight());
		textArea.setMaxWidth(getWidth());
		textArea.setMaxHeight(getHeight());
		
		displayedNode = textArea;
		
		if (gitObject.getType().equals("blob")) {
			textArea.setText(new String(((GitBlob) (gitObject)).getData()));
		} else if (gitObject.getType().equals("tree")) {
			textArea.setText(new String(((GitTree) (gitObject)).toString()));
		} else if (gitObject.getType().equals("tag")) {
			textArea.setText(new String(((GitTag) (gitObject)).toString()));
		} else if (gitObject.getType().equals("commit")) {
			textArea.setText(new String(((GitCommit) (gitObject)).toString()));
		}
		
		
		
		visionneuse.getChildren().add(displayedNode);
	}
}
