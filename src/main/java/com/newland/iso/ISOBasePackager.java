package com.newland.iso;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.message.Field;
import com.newland.message.FieldMap;
import com.newland.message.IField;
import com.newland.message.MessageException;
import com.newland.message.packager.IFieldPackager;
import com.newland.message.packager.MessagePackager;
import com.newland.posp.boc.BocIstPosMessage;
import com.newland.posp.mina.boc.IstPosProtocolEncoder;


/**
 * provides base functionality for the actual packagers
 *
 * @author apr@cs.com.uy
 * @version $Id: ISOBasePackager.java 2594 2008-01-22 16:41:31Z apr $
 * @see org.jpos.iso.packager.ISO87APackager
 * @see org.jpos.iso.packager.ISO87BPackager
 */
public abstract class ISOBasePackager implements MessagePackager {
	
	private final Logger logger = LoggerFactory.getLogger(ISOBasePackager.class);
	
    protected IFieldPackager[] fld;
    
    public void setFieldPackager (IFieldPackager[] fld) {
        this.fld = fld;
    }
    
    /**
     * @return true if BitMap have to be emited
     */
    protected boolean emitBitMap () {
        return (fld[1] instanceof ISOBitMapPackager);
    }
    
    /**
     * usually 2 for normal fields, 1 for bitmap-less
     * @return first valid field
     */
    protected int getFirstField() {
        if (!(fld[0] instanceof ISOMsgFieldPackager))
            return (fld[1] instanceof ISOBitMapPackager) ? 2 : 1;
        return 0;
    }
    
    /**
     * @param   m   the Component to pack
     * @return      Message image
     * @exception MessageException
     */
    public byte[] pack (FieldMap m) throws MessageException {
        try {
            if (m.getValue() != m) 
                throw new MessageException ("Can't call packager on non Composite");

            IField<?> c;
            ArrayList v = new ArrayList(128);
            Map<Integer,IField<?>> fields = ((FieldMap)m).getFields();
            int len = 0;
            int first = getFirstField();

            c = (IField<?>) fields.get (0);
            byte[] b;

            if (first > 0 && c != null) {
                b = fld[0].pack(c);
                len += b.length;
                v.add (b);
            }

            if (emitBitMap()) {
                // BITMAP (-1 in HashTable)
                c = (IField<?>) fields.get (new Integer (-1));
                b = getBitMapfieldPackager().pack(c);
                len += b.length;
                v.add (b);
            }

            // if Field 1 is a BitMap then we are packing an
            // ISO-8583 message so next field is fld#2.
            // else we are packing an ANSI X9.2 message, first field is 1
            int tmpMaxField=Math.min (((BocIstPosMessage)m).getMaxField(), 128);

            for (int i=first; i<=tmpMaxField; i++) {
                if ((c= fields.get (i)) != null)
                {
                    try {
                        IFieldPackager fp = fld[i];
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
        
            if(((BocIstPosMessage)m).getMaxField()>128 && fld.length > 128) {
                for (int i=1; i<=64; i++) {
                    if ((c = fields.get (i+128)) != null)
                    {
                        try {
                            b = fld[i+128].pack(c);
                            len += b.length;
                            v.add (b);
                        } catch (MessageException e) {
                            throw e;
                        }
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
        try {
            int consumed = 0;
            
            if (!(fld[0] instanceof ISOBitMapPackager))
            {
                IField<String> mti = (IField<String>)fld[0].createComponent(0);
                consumed  += fld[0].unpack(mti, b, consumed + offset);
                logger.debug("iso unpack...[mti]:"+mti.getValue());
                ((BocIstPosMessage)m).set (mti);
            }
            BitSet bmap = null;
            int maxField = fld.length;
            if (emitBitMap()) {
                IField<BitSet> bitmap = new Field<BitSet> (-1);
                consumed += getBitMapfieldPackager().unpack(bitmap,b,consumed+offset);
                bmap = (BitSet) bitmap.getValue();
                ((BocIstPosMessage)m).set (bitmap);
                maxField = Math.min(maxField, bmap.size());
                logger.debug("iso unpack...[maxField]:"+maxField);
            }
            for (int i=getFirstField(); i<maxField; i++) {
                try {
                    if (bmap == null && fld[i] == null)
                        continue;
                    if (maxField > 128 && i==65)
                        continue;   // ignore extended bitmap
                    
                    if (bmap == null || bmap.get(i)) {
                        if (fld[i] == null)
                            throw new MessageException ("field packager '" + i + "' is null");

                        IField<?> c = fld[i].createComponent(i);
                        consumed += fld[i].unpack (c, b, consumed + offset);
                        logger.debug("iso unpack...["+i+"]:"+c.getValue());
                        ((BocIstPosMessage)m).set(c);
                    }
                } catch (MessageException e) {
                    System.out.println("error unpacking field "+i);
                    e.printStackTrace(System.out);
                    throw e;
                }
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
            e.printStackTrace();
            throw new MessageException (e);
        } finally {
        }
    }
   
    /**
     * @param   m   the Container (i.e. an ISOMsg)
     * @param   fldNumber the Field Number
     * @return  Field Description
     */
    public String getFieldDescription(FieldMap m, int fldNumber) {
        return fld[fldNumber].getDescription();
    }
    /**
     * @param   fldNumber the Field Number
     * @return  Field Packager for this field
     */
    public IFieldPackager getFieldPackager (int fldNumber) {
        return fld[fldNumber];
    }
    /**
     * @param   fldNumber the Field Number
     * @param   fieldPackager the Field Packager
     */
    public void setFieldPackager 
        (int fldNumber, IFieldPackager fieldPackager) 
    {
        fld[fldNumber] = fieldPackager;
    }
    
    public BocIstPosMessage createISOMsg () {
        return new BocIstPosMessage();
    }
    /**
     * @return 128 for ISO-8583, should return 64 for ANSI X9.2
     */
    protected int getMaxValidField() {
        return 128;
    }
    /**
     * @return suitable ISOFieldPackager for Bitmap
     */
    protected IFieldPackager getBitMapfieldPackager() {
        return fld[1];
    }
   
}

