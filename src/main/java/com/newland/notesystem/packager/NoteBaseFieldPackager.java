package com.newland.notesystem.packager;

import com.newland.message.IField;
import com.newland.message.MessageException;
import com.newland.message.packager.FieldPackager;

public abstract class NoteBaseFieldPackager extends FieldPackager {

	private Object defaultValue;
	
	public NoteBaseFieldPackager(int len, String description){
		super(len,description);
	}
	
	public NoteBaseFieldPackager(int len, String description,Object defaultValue){
		super(len,description);
		this.defaultValue = defaultValue;
	}
	
	@Override
	public byte[] pack(IField<?> c) throws MessageException {
		Object value = null;
		if(c.getValue() == null){
			value = defaultValue;
		}else
			value = c.getValue();
		
		String vStr = unpack(value);
		
		if(vStr.length()>getLength())
			throw new MessageException("the length of field ["+getDescription()+"] out of range:" + vStr.length() + ",the definition： " + getLength());
		
		vStr = vStr+"|";
		byte [] b = vStr.getBytes();
		return b;
	}

	protected abstract String unpack(Object value) ;

	@Override
	public int unpack(IField<?> c, byte[] b, int offset)
			throws MessageException {
		throw new UnsupportedOperationException("暂时不需要实现的方法！  ");
	}

}
