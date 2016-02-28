package edu.caravane.guitare.application;

import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.*;

import java.util.ArrayList;

import edu.caravane.guitare.gitobejct.GitBlob;
import edu.caravane.guitare.gitobejct.GitCommit;
import edu.caravane.guitare.gitobejct.GitObject;
import edu.caravane.guitare.gitobejct.GitObjectReader;
import edu.caravane.guitare.gitobejct.GitObjectsIndex;
import edu.caravane.guitare.gitobejct.GitTag;
import edu.caravane.guitare.gitobejct.GitTree;
import edu.caravane.guitare.gitobejct.TreeEntry;
import javafx.application.Application;

public class MainWindow extends Application {
	protected final String osBarre = 
			System.getProperty("os.name").charAt(0) == 'W' ? "\\" : "/" ;
	protected GitObjectsIndex gitObjectsIndex;

	/**
	 * This function add all the objects into a GitObjectsIndex object
	 *
	 * @author Sylvain, VieVie31
	 *
	 * @param listObjs a list of paths (String) of git object
	 * @return the GitObjectsIndex containing all the objects by sha1
	 * @throws Exception if something wrong appends
	 */
	public GitObjectsIndex indexObjects(String[] listObjs) throws Exception {
			GitObjectReader gor;
			GitObjectsIndex goi =  GitObjectsIndex.getInstance();

			for (String pathObj : listObjs) {
				//on ne traite pas les pack pour le moment
				if (pathObj.contains(osBarre+"pack"+osBarre))
					continue;

				gor = new GitObjectReader(pathObj);
				goi.put(gor.getId(), gor.builGitObject());
			}

			return goi;
	}
	
	public void parcoursTree(GitTree tree){
		//On r�cup�re les �l�ments dans le tree
		GitObjectsIndex goi = GitObjectsIndex.getInstance();
		ArrayList<TreeEntry> treeEntry = tree.listEntry();
		//On parcours les �l�ments
		for(int i = 0; i < treeEntry.size(); i++){

			//On regarde si c'est un blob

			if(goi.get(treeEntry.get(i).getSha1()).getType().equals("blob")){
				//Si c'est un blob, on lui donne son nom et le parent
				GitBlob blob = (GitBlob) goi.get(treeEntry.get(i).getSha1());
				blob.addName(treeEntry.get(i).getName());
				blob.addParent(treeEntry.get(i).getSha1());
			}
			//On regarde si c'est un arbre
			else if(goi.get(treeEntry.get(i).getSha1()).getType().equals("tree")){
				//On regarde si c'est un arbre diff�rent de lui-m�me pour ne pas 
				//boucler � l'infini
				if(tree.getId() != treeEntry.get(i).getSha1()){
					//On ajoute ces parents et on le parcours ( r�cursivit� powa)
					GitTree treeSon = (GitTree) goi.get(treeEntry.get(i).getSha1());
					treeSon.addParent(tree.getId());
					parcoursTree(treeSon);
				}
			}
			
		}
	}


	public void makeLinks()throws Exception{
		GitObjectsIndex goi = GitObjectsIndex.getInstance();
		ArrayList<String> sha1Keys = goi.getListOfAllObjectKeys();
		for( String p : sha1Keys){
			
			if( goi.get(p).getType().equals("tag")){
				GitTag tag = (GitTag) goi.get(p);
				//On r�cup�re l'objet tagger
				GitObject tagger = goi.get(tag.getObjHexId());
				//On ajoute son parent ( le taggeur ) 
				tagger.addParent(tag.getId());
				
			}
			else if( goi.get(p).getType().equals("commit")){
				
				GitCommit commit = (GitCommit) goi.get(p);
				//En attendant que l'on trouve mieux.
				for(int i = 0; i < commit.getParentListId().size(); i++)
					commit.addParent(commit.getParentListId().get(i));
				
				//On r�cup�re le tree du commit
				GitTree treeCommit = (GitTree) goi.get(commit.getTreeId());
				//On ajoute le parent du tree
				treeCommit.addParent(commit.getId());
				//On appelle la fonction r�cursive qui parcours l'arbre
				parcoursTree(treeCommit);
				
			}
				
			
		}
		test();
	}
	
	private void test()throws Exception{ 
		GitObjectsIndex goi = GitObjectsIndex.getInstance();
		for(String te : goi.get("14163189dcfa37b2f52ecac7a8ff5979930e6a5b").getParentFiles()){
			System.out.println(te);
		}
		
		System.out.println(goi.get("14163189dcfa37b2f52ecac7a8ff5979930e6a5b"));
	}

	public void start(Stage primaryStage, String[] args) throws Exception {
		gitObjectsIndex = indexObjects(args);
		indexObjects(args);
		makeLinks();
		start(primaryStage);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root;
		root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
		Scene scene = new Scene(root);


		primaryStage.setTitle("GitExplorer");
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
