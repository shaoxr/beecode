package com.newland.tlv;

import com.newland.iso.AsciiInterpreter;
import com.newland.iso.AsciiPrefixer;
import com.newland.iso.Interpreter;
import com.newland.iso.Prefixer;
import com.newland.message.Field;
import com.newland.message.IField;
import com.newland.message.MessageException;
import com.newland.message.packager.IFieldPackager;
import com.newland.posp.utils.Dump;

public abstract class TLVFieldPackager implements IFieldPackager{
	protected Interpreter interpreter;
    protected Prefixer tagPrefixer;
    protected Prefixer lenPrefixer;
    
    /**
	 * 字段最大长度（或者定长）
	 */
    private int length;
    
    /**
     * 字段描述
     */
    private String description;
    
    
    /**
     * Default Constructor
     */
    public TLVFieldPackager()
    {
    	this(-1, null);
    }
    
    public TLVFieldPackager(int length, String desc)
    {
        this.length = length;
        this.description = desc;
        interpreter = AsciiInterpreter.INSTANCE;
        tagPrefixer = AsciiPrefixer.LL;
        lenPrefixer = AsciiPrefixer.LL;
    }
    
	public void setInterpreter(Interpreter interpreter) {
		this.interpreter = interpreter;
	}
	
	public void setTagPrefixer(Prefixer tagPrefixer) {
		this.tagPrefixer = tagPrefixer;
	}
	
	public void setLenPrefixer(Prefixer lenPrefixer) {
		this.lenPrefixer = lenPrefixer;
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/** Create a nice readable message for errors */
    protected String makeExceptionMessage(IField<?> c, String operation) {
        Object fieldKey = "unknown";
        if (c != null)
        {
            try
            {
                fieldKey = new Integer(c.getFieldNumber());
            } catch (Exception ignore)
            {
            }
        }
        return this.getClass().getName() + ": Problem " + operation + " field " + fieldKey;
    }
    
    /**
     * 将Field的值转换为字符串
     * @param field
     * @return
     */
    protected abstract String getAsString(IField<?> field);

    protected abstract void setFieldValue(IField<?> field, String value) throws MessageException;
    
    public byte[] pack (IField<?> c) throws MessageException {
    	try {
    		String data = getAsString(c);
            
            if (data.length() > getLength())
            {
                throw new MessageException("Field length " + data.length() + " too long. Max: " + getLength());
            }
            
            byte[] rawData = interpreter.interpret(data);
            byte[] d = new byte[tagPrefixer.getPackedLength() + lenPrefixer.getPackedLength() + rawData.length];
            tagPrefixer.encodeLength(c.getFieldNumber(), d);
            // TagLength为原始数据的长度
            lenPrefixer.encodeLength(rawData.length, d, tagPrefixer.getPackedLength());
            System.arraycopy(rawData, 0, d, tagPrefixer.getPackedLength()+lenPrefixer.getPackedLength(), rawData.length);
            return d;
        } catch(Exception e)
        {
        	e.printStackTrace();
            throw new MessageException(makeExceptionMessage(c, "packing"), e);
        }
    }

 
    public int unpack (IField<?> c, byte[] b, int offset)
        throws MessageException
    {
    	
    	int len = lenPrefixer.decodeLength(b, offset);
    	Dump.getHexDump(b);
		String unpacked = interpreter.uninterpret(b, offset + lenPrefixer.getPackedLength(), len);
		System.out.println("result:"+unpacked);
		setFieldValue(c, unpacked);
		
		return lenPrefixer.getPackedLength() + len;
    }

    
    /**
     * 默认创建字符串字段
	 */
    public IField<?> createComponent(int fldNo) {
    	return new Field<String>(fldNo);
    }
}
