package com.newland.notesystem.packager;

import java.text.SimpleDateFormat;

public class NoteStringFieldPackager extends NoteBaseFieldPackager  {
	
	public NoteStringFieldPackager(int len, String description){
		super(len,description);
	}
	
	public NoteStringFieldPackager(int len, String description,Object defaultValue){
		super(len,description,defaultValue);
	}

	@Override
	protected String unpack(Object value) {
		return (String)value;
	}
	
	
}
