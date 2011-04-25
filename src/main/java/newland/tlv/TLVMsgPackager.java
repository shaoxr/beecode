package com.newland.tlv;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.message.FieldMap;
import com.newland.message.IField;
import com.newland.message.MessageException;
import com.newland.message.packager.IFieldPackager;
import com.newland.message.packager.MessagePackager;

public class TLVMsgPackager implements MessagePackager {
	private final Logger logger = LoggerFactory.getLogger(TLVMsgPackager.class);
	protected IFieldPackager[] fieldPackagers;
    protected int headerLength = 0;
    
    public void setFieldPackager (IFieldPackager[] fld) {
        this.fieldPackagers = fld;
    }
    
    /**
     * @param   m   the Component to pack
     * @return      Message image
     * @exception MessageException
     */
    public byte[] pack (FieldMap m) throws MessageException {
        try {
            IField<?> c;
            ArrayList<byte[]> v = new ArrayList<byte[]>(128);
            byte[] b;
            int len = 0;

            int tmpMaxField = m.getMaxField();
            
            for (int i=0; i<=tmpMaxField; i++) {
                if ((c= m.getField (i)) != null)
                {
                    try {
                        IFieldPackager fp = fieldPackagers[i];
                        if (fp == null)
                            throw new MessageException ("null field "+i+" packager");
                        b = fp.pack(c);
                        len += b.length;
                        v.add (b);
                    } catch (MessageException e) {
                        throw e;
                    }
                }
            }
        
            byte[] d = new byte[len];
            
            for (int k=0,i=0; i<v.size(); i++) {
                b = (byte[]) v.get(i);
                for (int j=0; j<b.length; j++)
                    d[k++] = b[j];
            }
            return d;
        } catch (MessageException e) {
            throw e;
        } finally {
        }
    }
    
    public int unpack(FieldMap m, byte[] b) throws MessageException
    {
    	return unpack(m, b, 0, b.length);
    }

    /**
     * @param   m   the Container of this message
     * @param   b   ISO message image
     * @param	offset the begin
     * @param	len unpack max len
     * @return      consumed bytes
     * @exception MessageException
     */
    public int unpack (FieldMap m, byte[] b, int offset, int len) throws MessageException {
        int consumed = 0;
        if(logger.isDebugEnabled()){
        	logger.debug("Unpack msg =[" + new String(b) + "].");
        }
    	try {
            while(consumed < len) {
                	int tag = 0;
                	
                	// Tag 占两个字节  
                	
                	tag = getTag(b, offset + consumed);
                	logger.debug("offset:"+offset+"consumed:"+consumed);
                	consumed += 2;
                	if (logger.isDebugEnabled()) logger.debug("Unpacking field "+tag);
                    if (tag> fieldPackagers.length || fieldPackagers[tag] == null)
                            throw new MessageException ("field packager '" + tag + "' is null");
                    
                    if (logger.isDebugEnabled()) 
                    	logger.debug("unpacking tag "+tag+" "+fieldPackagers[tag].getDescription());
                    
                    IField<?> c = fieldPackagers[tag].createComponent(tag);
                    
                    consumed += fieldPackagers[tag].unpack (c, b, consumed + offset);
                    
                    m.set(c);
            }
            
            if (len != consumed) {
                //evt.addMessage (
                //    "WARNING: unpack len=" +b.length +" consumed=" +consumed
                //);
            }
            return consumed;
        } catch (MessageException e) {
            throw e;
        } catch (Exception e) {
            throw new MessageException (e);
        } finally {
        }
    }

	private int getTag(byte[] b, int offset)
	{
		int tag = 0;
		tag = (b[offset] & 0xFF) - '0';
		tag = tag * 10 + ((b[offset+1] & 0xFF) - '0');
		return tag;
	}
	
   
    /**
     * @param   m   the Container (i.e. an ISOMsg)
     * @param   fldNumber the Field Number
     * @return  Field Description
     */
    public String getFieldDescription(FieldMap m, int fldNumber) {
        return fieldPackagers[fldNumber].getDescription();
    }
    
    /**
     * @param   fldNumber the Field Number
     * @return  Field Packager for this field
     */
    public IFieldPackager getFieldPackager (int fldNumber) {
        return fieldPackagers[fldNumber];
    }
    
    /**
     * @param   fldNumber the Field Number
     * @param   fieldPackager the Field Packager
     */
    public void setFieldPackager 
        (int fldNumber, IFieldPackager fieldPackager) 
    {
    	fieldPackagers[fldNumber] = fieldPackager;
    }
}
