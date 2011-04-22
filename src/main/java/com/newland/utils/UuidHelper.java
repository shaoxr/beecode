package com.newland.utils;



import java.util.UUID;

public class UuidHelper {

	public static String generateUUID(){
		//UUID uid = Generators.timeBasedGenerator().generate();
		return UUID.randomUUID().toString();
	}
}

