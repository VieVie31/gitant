package edu.caravane.guitare.gitobejct;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class GitObject implements GitObjectInterface {
	protected String sha1;
	protected int size;

	protected ArrayList<String> names = new ArrayList<String>();
	protected ArrayList<String> parents = new ArrayList<String>();
	protected GitDate date = new GitDate(0); //What I haven't for 14/02

	public abstract GitObjectType getType();
	public abstract int getSize();
	public abstract String getId();

	public GitDate date() {
		return date;
	}

	@Override
	public void setDate(GitDate date) {
		System.out.println("J'aime bien les chips wesh");
		this.date = date;
		System.out.println(this.date);
	}

	@Override
	public void addName(String name) {
		names.add(name);
	}

	@Override
	public void addParent(String parentFileName) {
		parents.add(parentFileName);
	}

	@Override
	public void addParents(String[] parentsFilesNames) {
		for (String parent : parentsFilesNames)
			if (!parents.contains(parent))
				parents.add(parent);
	}

	@Override
	public String getDate() {
		return date.toString();
	}

	@Override
	public String[] getNames() {
		return names.toArray(new String[names.size()]);
	}

	@Override
	public String[] getParentFiles() {
		return parents.toArray(new String[parents.size()]);
	}
	public StringProperty sha1Property() {
		StringProperty sha1 = new SimpleStringProperty(this.getId());
		return sha1;
	}

	public StringProperty typeProperty() {
		StringProperty type = new SimpleStringProperty(
				this.getType().toString());
		return type;
	}

	public StringProperty sizeProperty() {
		StringProperty size  = new SimpleStringProperty(
				Integer.toString(this.getSize()));
		return size;
	}
	public StringProperty nameProperty() {
		StringProperty name  = new SimpleStringProperty(this.names.get(0));
		return name;
	}
}
