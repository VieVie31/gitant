package edu.caravane.guitare.test;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GitTest {
	private final StringProperty sha1;
	private final StringProperty type;
	private final StringProperty nom;
	private final StringProperty size;
	private final StringProperty date;


	public GitTest(String sha, String type, String nom, String size, String date) {
		this.sha1 = new SimpleStringProperty(sha);
		this.type = new SimpleStringProperty(type);
		this.nom = new SimpleStringProperty(nom);
		this.size = new SimpleStringProperty(size);
		this.date = new SimpleStringProperty(date);
	}
	
	public String getSha1() {
		return sha1.get();
	}

	public void setSha1(String sha1) {
		this.sha1.set(sha1);
	}
	
	public StringProperty sha1Property() {
		return sha1;
	}

	public String getType() {
		return type.get();
	}

	public void setType(String type) {
		this.type.set(type);
	}
	
	public StringProperty typeProperty() {
		return type;
	}
	
	public String getNom() {
		return nom.get();
	}

	public void setNom(String nom) {
		this.nom.set(nom);
	}
	
	public StringProperty nomProperty() {
		return nom;
	}
	
	public String getSize() {
		return size.get();
	}

	public void setSize(String size) {
		this.size.set(size);
	}
	
	public StringProperty sizeProperty() {
		return size;
	}
	
	public String getDate() {
		return date.get();
	}

	public void setDate(String date) {
		this.date.set(date);
	}
	
	public StringProperty dateProperty() {
		return date;
	}
}


