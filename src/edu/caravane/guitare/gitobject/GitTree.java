package edu.caravane.guitare.gitobject;
import java.util.*;

public class GitTree extends GitObject {
	protected static GitObjectType type = GitObjectType.TREE;
	protected int size;
	protected String sha1;
	protected ArrayList<TreeEntry> lstEntr;

	public GitTree(int size, String sha1, ArrayList<TreeEntry> lstEntr) {
		this.lstEntr = lstEntr;
		this.size = size;
		this.sha1 = sha1;
	}

	public GitTree(long size, String sha1, byte[] data) {
		this.size = (int) size;
		this.sha1 = sha1;
		this.setData(data);
	}

	private void setData(byte[] data) {
		this.lstEntr = new ArrayList<TreeEntry>();
		StringBuffer buffer = new StringBuffer();
		StringBuffer lineBuffer = new StringBuffer();
		boolean trad = false;
		int cpt = 0;
		for (byte by : data) {
			if (by == 0 && !trad) {
				buffer.append((char) 32);
				lineBuffer.append((char) 32);
				cpt = 20;
				trad = true;
			} else {
				if (trad) {
					String string = Integer.toHexString(by);
					if (string.length() > 2){
						string = string.substring(string.length() - 2);
					}
					else if(string.length() == 1){
						string = "0" + string;
					}
					buffer.append(string);
					lineBuffer.append(string);
					cpt--;
					if (cpt <= 0) {
						trad = false;
						buffer.append("\n");
						TreeEntry a = new TreeEntry(lineBuffer.toString());
						lstEntr.add(a);
						lineBuffer.setLength(0);
					}
				} else {
					lineBuffer.append((char) by);
					buffer.append((char) by);
				}
			}
		}
	}

	/**
	 * Getter
	 *
	 * @author Sylvain
	 *
	 * @return the type of the object
	 */
	@Override
	public GitObjectType getType() {
		return GitTree.type;
	}

	/**
	 * Getter
	 *
	 * @author Sylvain
	 *
	 * @return the size of the object
	 */
	@Override
	public int getSize() {
		return this.size;
	}

	/**
	 * Getter
	 *
	 * @author Sylvain
	 *
	 * @return the id of the object
	 */
	@Override
	public String getId() {
		return this.sha1;
	}

	/**
	 * Getter
	 *
	 * @author Sylvain
	 *
	 * @return The list of the entry
	 */
	public ArrayList<TreeEntry> listEntry() {
		return this.lstEntr;
	}

	public void addEntry(TreeEntry te) {
		this.lstEntr.add(te);
	}

	public String toString() {
		String s = "Tree : " + sha1 + "\nChildren : \n";
		for (TreeEntry te : lstEntr)
			s += te.toString() + "\n";
		return s;
	}

}
