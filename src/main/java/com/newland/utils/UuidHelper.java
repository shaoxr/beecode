package com.newland.utils;


import java.util.UUID;

import com.fasterxml.uuid.Generators;

public class UuidHelper {

	public static String generateUUID(){
		UUID uid = Generators.timeBasedGenerator().generate();
		return uid.toString();
	}
}

