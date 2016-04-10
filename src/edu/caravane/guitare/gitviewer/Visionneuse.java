package edu.caravane.guitare.gitviewer;

import javafx.scene.*;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.DataFormatException;

import javax.imageio.ImageIO;

import edu.caravane.guitare.gitobject.GitBlob;
import edu.caravane.guitare.gitobject.GitCommit;
import edu.caravane.guitare.gitobject.GitObject;
import edu.caravane.guitare.gitobject.GitObjectsIndex;
import edu.caravane.guitare.gitobject.GitTag;
import edu.caravane.guitare.gitobject.GitTree;


public class Visionneuse extends Parent {
	protected static Visionneuse visionneuse;
	protected static AnchorPane visionneuseAP;
	protected static Node displayedNode;
	
	/**
	 * This function set the AnchorPane of the visionneuse.
	 *
	 * @author TheHaricover
	 */
	public void setAp(AnchorPane visionneuseAP) {
		Visionneuse.visionneuseAP = visionneuseAP;
	}
	
	/**
	 * This function is used to get the width of the AnchorPane 
	 * associated with the visionneuse.
	 *
	 * @author TheHaricover
	 */
	public static double getWidth() {
		return visionneuseAP.getWidth();
	}
	
	/**
	 * This function is used to get the height of the AnchorPane 
	 * associated with the visionneuse.
	 *
	 * @author TheHaricover
	 */
	public static double getHeight() {
		return visionneuseAP.getHeight();
	}
	
	/**
	 * This function is used to set the width of the AnchorPane 
	 * associated with the visionneuse.
	 *
	 * @author TheHaricover
	 */
	public static void setWidth(double width) {
		visionneuseAP.widthProperty().subtract(visionneuseAP.getWidth()).add(width);
	}
	
	/**
	 * This function is used to set the height of the AnchorPane 
	 * associated with the visionneuse.
	 *
	 * @author TheHaricover
	 */
	public static void setHeight(double height) {
		visionneuseAP.heightProperty().subtract(visionneuseAP.getHeight()).add(height);
	}
	
	/**
	 * This function is used to resize the visionneuse.
	 *
	 * @author TheHaricover
	 */
	public static void resize(String hash) throws IOException, DataFormatException {
		if (displayedNode == null)
			return;
		if (displayedNode instanceof TextArea){
			display(hash);
		}
	}
	
	/**
	 * This function is used to get the instance of the visionneuse.
	 * If there is no instance, it's created.
	 *
	 * @author TheHaricover
	 */
	public static Visionneuse getInstance() throws IOException {
		if (Visionneuse.visionneuse == null){
			Visionneuse.visionneuse = new Visionneuse();
		}
		return Visionneuse.visionneuse;
	}
	
	/**
	 * This function is used to call the display function
	 * with the GitObject corresponding to the hash.
	 *
	 * @author TheHaricover
	 */
	public static void display(String hash) throws IOException, DataFormatException {
		GitObject gitObject = GitObjectsIndex.getInstance().get(hash);
		display(gitObject);
	}

	/**
	 * This function is used to display the GitObject given.
	 *
	 * @author TheHaricover
	 */
	public static void display(GitObject gitObject) throws IOException, DataFormatException {
		Visionneuse visionneuse = Visionneuse.getInstance();
		visionneuse.getChildren().clear();
		
		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setMinWidth(getWidth());
		textArea.setMinHeight(getHeight());
		textArea.setMaxWidth(getWidth());
		textArea.setMaxHeight(getHeight());
		
		ImageView imageView = new ImageView ();
		imageView.setFitWidth(getWidth());
		imageView.setFitHeight(getHeight());
		
		if (gitObject.getType().equals("blob")) {
			String nom = gitObject.nameProperty().get().toLowerCase();
			if (nom.contains(".bmp") || nom.contains(".png") || nom.contains(".jpeg") || nom.contains(".jpg") || nom.contains(".gif")){
				byte[] data = ((GitBlob) gitObject).getData();
				textArea.setText(new String(data));
				try {
					//saving temporary the data
					String tmpFileName = String.format("%d.tmp", System.currentTimeMillis());
					FileOutputStream fos = new FileOutputStream(tmpFileName);
					fos.write(data);
					fos.close();
					try { 
						BufferedImage bf = ImageIO.read(new File(tmpFileName));
						WritableImage wr = null;
				        if (bf != null) {
				            wr = new WritableImage(bf.getWidth(), bf.getHeight());
				            PixelWriter pw = wr.getPixelWriter();
				            for (int x = 0; x < bf.getWidth(); x++) {
				                for (int y = 0; y < bf.getHeight(); y++) {
				                    pw.setArgb(x, y, bf.getRGB(x, y));
				                }
				            }
				        }
				        ImageView imgView = new ImageView(wr);
				        imgView.setPreserveRatio(true);
				        imgView.resize(getWidth(), getHeight());
				        imgView.setFitHeight(getHeight());
				        imgView.setFitWidth(getWidth());
				        Image image = imgView.getImage();
				        imageView.setImage(image);
				        visionneuse.getChildren().add(imageView);
				        }
					catch (Exception e) {
					}
					Files.delete(new File(tmpFileName).toPath());
				} catch (Exception e) {
					textArea.setText(new String(data));
					visionneuse.getChildren().add(textArea);
				}
			}else{
				textArea.setText(new String(((GitBlob) (gitObject)).getData()));
				visionneuse.getChildren().add(textArea);
			}
		} else if (gitObject.getType().equals("tree")) {
			textArea.setText(new String(((GitTree) (gitObject)).toString()));
			visionneuse.getChildren().add(textArea);
		} else if (gitObject.getType().equals("tag")) {
			textArea.setText(new String(((GitTag) (gitObject)).toString()));
			visionneuse.getChildren().add(textArea);
		} else if (gitObject.getType().equals("commit")) {
			textArea.setText(new String(((GitCommit) (gitObject)).toString()));
			visionneuse.getChildren().add(textArea);
		}
	}
}
