package edu.caravane.guitare.gitobejct;

import java.sql.Timestamp;
import java.util.Date;

public class GitDate extends Timestamp{
	protected int offset;
	
	@SuppressWarnings("deprecation")
	public GitDate(long time) {
		super(time);
	}
	
	public void setOffset(int off) {
		this.offset = off;
	}
	
	public int getOffset() {
		return this.offset;
	}
	
	@Override
	public int compareTo(Date o) {
		return super.compareTo(o);
	}
}
