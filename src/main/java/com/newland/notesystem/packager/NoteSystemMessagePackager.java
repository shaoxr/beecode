package com.newland.notesystem.packager;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newland.message.FieldMap;
import com.newland.message.MessageException;
import com.newland.message.packager.FieldPackager;
import com.newland.message.packager.MessagePackager;
import com.newland.notesystem.NoteMessage;

public class NoteSystemMessagePackager implements MessagePackager {

	public static final NoteSystemMessagePackager INSTANCE = new NoteSystemMessagePackager();
	
	private Log log = LogFactory.getLog(this.getClass().getName());
	
	private static FieldPackager fld[];
	
	static{
		fld[0] = new NoteStringFieldPackager(4,"srvid","9000");
		fld[1] = new NoteStringFieldPackager(1,"srvtype","0");
		fld[2] = new NoteStringFieldPackager(30,"srvaccno","");
		fld[3] = new NoteStringFieldPackager(11,"objaddr");
		fld[4] = new NoteStringFieldPackager(1,"agentno");
		fld[5] = new NoteStringFieldPackager(255,"msgcont");
		fld[6] = new NoteDateFieldPackager(8,"crtdate","yyyyMMdd");
		fld[7] = new NoteDateFieldPackager(6,"crttime","HHmmss");
		fld[8] = new NoteStringFieldPackager(20,"reserve","");
	}
	
	public String getFieldDescription(FieldMap m, int fldNumber) {

		return fld[fldNumber].getDescription();
	}

	public byte[] pack(FieldMap m) throws MessageException {
		int length = fld.length;
		int len = 0;
		ArrayList <byte[]> v = new ArrayList <byte[]>();
		
		for(int i=0;i<=length;i++){
			if(m.getField(i) == null){
				m.set(fld[i].createComponent(i));
			}
			byte[] bytes = fld[i].pack(m.getField(i));
			v.add(bytes);
			len += bytes.length;
		}
		byte [] result = new byte[len];
		
        for (int k=0,i=0; i<v.size(); i++) {
        	byte[] bytes = (byte[]) v.get(i);
            for (int j=0; j<bytes.length; j++)
            	result[k++] = bytes[j];
        }
		
		return result;
	}

	public int unpack(FieldMap m, byte[] b, int offset, int len)
			throws MessageException {
		unpack(m,b,0,b.length);
		return 0;
	}

	public int unpack(FieldMap m, byte[] b) throws MessageException {
		if(b.length != 4){
			log.error("note system response with error length:"+b.length);
		}
		for(int i=0;i<b.length;i++){
			if(b[i]!='0'){
				((NoteMessage)m).setSucceed(false);
			}
		}
		((NoteMessage)m).setSucceed(true);
		return 0;
	}

}
