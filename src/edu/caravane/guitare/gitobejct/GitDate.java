package edu.caravane.guitare.gitobejct;

import java.sql.Timestamp;

@SuppressWarnings("serial")
public class GitDate extends Timestamp {
	protected int date;
	protected int offset;
	
	public GitDate(int date) {
		super(date);
		this.offset = 0;
	}
	
	public GitDate(int date, int offset) {
		super(date + offset);
		this.offset = offset;
		this.date = date + offset;
	}
	
	//getter
	
	public int getDate() {
		return this.date;
	}
	
	public int getOffset() {
		return this.offset;
	}
	
	public int getDateWithoutOffset() {
		return this.date - this.offset;
	}
	
	public int compareTo(GitDate date) {
		return super.compareTo(date);
	}
	
	public int compareToWithOffset(GitDate dateWithOff) {
		return super.compareTo(dateWithOff);
	}
	
	public String toString() { //temporaire plus propre plus tard...
		return String.format("%d %d", date, offset);
	}
}
