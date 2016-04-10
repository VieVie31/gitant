package edu.caravane.guitare.gitobject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitTag extends GitObject {
	protected static GitObjectType type = GitObjectType.TAG;
	protected int size;
	protected String tagName, sha1;
	protected String objHexId;
	protected GitInfo tagger;
	protected String tagType;
	protected byte[] data;

	public GitTag(int size, String sha1, String objHexId, String tagType,
			String tagName, GitInfo tagger, byte[] data) {
		this.tagName = tagName;
		this.size = size;
		this.sha1 = sha1;
		this.objHexId = objHexId;
		this.tagType = tagType;
		this.tagger = tagger;
		this.data = data;
	}

	public GitTag(long size, String sha1, String data) {
		this.size = (int) size;
		this.sha1 = sha1;
		this.setData(data);
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
		return GitTag.type;
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
	 * @return the datas from a tag object
	 */
	public byte[] getData() {
		return this.data;
	}

	/**
	 * Getter
	 *
	 * @author Sylvain
	 *
	 * @return the tag name from a tag object
	 */
	public String getTagName() {
		return this.tagName;
	}

	/**
	 * Getter
	 *
	 * @author Sylvain
	 *
	 * @return the tagger of a tag object
	 */
	public GitInfo getTagger() {
		return this.tagger;
	}

	/**
	 * *Getter
	 *
	 * @author Marvyn
	 *
	 * @return the sha1 of the object tagged
	 */
	public String getObjHexId(){
		return this.objHexId;
	}

	private void setData(String data) {
		Pattern lines = Pattern.compile("\n");
		String[] datab = lines.split(data);
		String tagMail = null;
		String tagDate = null;
		String tagTagger = null;

		this.objHexId = datab[0].substring(7);

		this.tagType = datab[1].substring(5);

		this.tagName = datab[2].substring(4);

		Pattern taggerName = Pattern.compile("[A-Z][a-z]+ [A-Z][a-z]+");
		Matcher tagger = taggerName.matcher(datab[3]);
		while (tagger.find()) {
			tagTagger = tagger.group();
		}

		Pattern taggerMail = Pattern.compile("<.*>");
		Matcher mail = taggerMail.matcher(datab[3]);
		while (mail.find()) {
			tagMail = mail.group();
		}

		Pattern taggerDate = Pattern.compile("[0-9]* \\+[0-9]*");
		Matcher date = taggerDate.matcher(datab[3]);
		while (date.find()) {
			tagDate = date.group();
		}

		GitDate tagDat = new GitDate(Integer.parseInt(tagDate));
		GitInfo tagInfo = new GitInfo(tagTagger, tagMail, tagDat);
		this.tagger = tagInfo;
	}

	public String toString() {
		String s = "";
		s += "object : " + objHexId + "\n";
		s += "tag type : " + tagType + "\n";
		s += "tag name : " + tagName + "\n";
		s += "tagger : " + tagger.toString() + "\n";
		s += new String(data);
		return s;
	}
}