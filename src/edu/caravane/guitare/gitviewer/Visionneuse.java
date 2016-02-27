package edu.caravane.guitare.gitviewer;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.zip.DataFormatException;

import edu.caravane.guitare.gitobejct.GitBlob;
import edu.caravane.guitare.gitobejct.GitObject;
import edu.caravane.guitare.gitobejct.GitObjectsIndex;


public class Visionneuse extends Parent {
	protected static Visionneuse visionneuse;
	
	protected Visionneuse() throws IOException {
		Parent p = FXMLLoader.load(getClass().getResource("Visioneuse.fxml"));
		AnchorPane visioneuseAP = (AnchorPane) p.lookup("#visioneuseAP");
		getChildren().add(visioneuseAP);
	}
	
	public static Visionneuse getInstance() throws IOException {
		if (Visionneuse.visionneuse == null)
			Visionneuse.visionneuse = new Visionneuse();
		return Visionneuse.visionneuse;
	}
	
	public static void display(String hash) throws IOException, DataFormatException {
		GitObject gitObject = GitObjectsIndex.getInstance().get(hash);
		display(gitObject);
	}

	public static void display(GitObject gitObject) throws IOException, DataFormatException {
		Visionneuse visionneuse = Visionneuse.getInstance();
		TextArea textArea = (TextArea) visionneuse.lookup("#visioneuseTV");
		Label hashLabel = (Label) visionneuse.lookup("#hashLabel");
		
		//actualiser le label
		hashLabel.setText("Hash : " + gitObject.getId());
		
		//actualiser la visionneuse texte
		if (gitObject.getType().equals("blob"))
			textArea.setText(new String(((GitBlob) gitObject).getData()));
		else
			textArea.setText(gitObject.toString());
		
		//faire le reste
	}
}
