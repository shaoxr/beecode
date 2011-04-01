package com.newland.notesystem.packager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteDateFieldPackager extends NoteBaseFieldPackager {

	private SimpleDateFormat sdf;
	
	public NoteDateFieldPackager(int len, String description,String pattern){
		super(len,description);
		sdf = new SimpleDateFormat(pattern);
	}
	
	public NoteDateFieldPackager(int len, String description,Object defaultValue,String pattern){
		super(len,description,defaultValue);
		sdf = new SimpleDateFormat(pattern);
	}

	@Override
	protected String unpack(Object value) {
		return sdf.format((Date)value);
	}

}
