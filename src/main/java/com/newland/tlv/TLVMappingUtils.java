package com.newland.tlv;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.newland.message.Field;
import com.newland.message.FieldMap;
import com.newland.message.MessageException;
import com.newland.posp.boc.BocDepotPosMessage;

public class TLVMappingUtils {

	public static final Map fieldMapIntoMapForTLV(FieldMap fieldMap){
		Set<Integer> keySet = fieldMap.getFieldNumbers();
		Map <Integer,Object> resultMap = new HashMap<Integer,Object>();
		for(Integer i:keySet){
			Object value = fieldMap.getField(i);
			if(value instanceof Field){
				Field<Object> field = (Field<Object>)value;
				resultMap.put(i, field.getValue());
			}
		}
		return resultMap;
	}
	
	public static final FieldMap mapIntoFieldMapForTLV(Map<Integer,Object> map,BocDepotPosMessage message){
		Set<Integer> keySet = map.keySet();
		message.clear();
		for(Integer i:keySet){
			Object o = map.get(i);
			Field<Object> field = new Field<Object>(i);
			try {
				field.setValue(o);
				message.set(field);
			} catch (MessageException e) {
				e.printStackTrace();
				continue;
			}
		}
		return message;
	}
	
	
}
