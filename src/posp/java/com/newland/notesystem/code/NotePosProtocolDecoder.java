package com.newland.notesystem.code;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.newland.message.packager.MessagePackager;
import com.newland.notesystem.NoteMessage;
import com.newland.notesystem.packager.NoteSystemMessagePackager;

public class NotePosProtocolDecoder extends ProtocolDecoderAdapter {

	public void decode(IoSession session, ByteBuffer buffer,
			ProtocolDecoderOutput arg2) throws Exception {
		// TODO Auto-generated method stub
		MessagePackager messagePacker = NoteSystemMessagePackager.INSTANCE;
		NoteMessage m = new NoteMessage();
		byte[] b = new byte[4];
		buffer.get(b);
		messagePacker.unpack(m, b);
		session.write(b);
	}

}
