package com.newland.notesystem.code;

import java.nio.ByteBuffer;

import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.newland.message.packager.MessagePackager;
import com.newland.notesystem.NoteMessage;
import com.newland.notesystem.packager.NoteSystemMessagePackager;

public class NotePosProtocolEncoder extends ProtocolEncoderAdapter {

	public void encode(IoSession session, Object message, ProtocolEncoderOutput arg2)
			throws Exception {
		// TODO Auto-generated method stub
		NoteMessage noteMessage = (NoteMessage)message;
		MessagePackager messagePacker = NoteSystemMessagePackager.INSTANCE;
		byte [] b = messagePacker.pack(noteMessage);
		ByteBuffer buffer = ByteBuffer.allocate(b.length);
		buffer.put(b);
		
		buffer.flip();
		session.write(buffer);
	}
}
