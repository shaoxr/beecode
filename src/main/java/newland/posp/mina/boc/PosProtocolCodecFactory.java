package com.newland.posp.mina.boc;

import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class PosProtocolCodecFactory implements ProtocolCodecFactory {
	private AbstractPosProtocolEncoder encoder;

    private AbstractPosProtocolDecoder decoder;
    
    public PosProtocolCodecFactory(AbstractPosProtocolEncoder encoder, AbstractPosProtocolDecoder decoder) {
    	this.decoder = decoder;
    	this.encoder = encoder;
    }
    
	public ProtocolDecoder getDecoder() throws Exception {
		return this.decoder;
	}

	public ProtocolEncoder getEncoder() throws Exception {
		return this.encoder;
	}

}
