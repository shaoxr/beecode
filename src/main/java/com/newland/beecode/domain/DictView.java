package com.newland.beecode.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findDictViewsByKeyName" })
public class DictView {

    @NotNull
    private String keyName;

    @NotNull
    private String keyValue;

    @NotNull
    private String keyDesc;

    public static final Map<String, Map<String, String>> dicts = new HashMap<String, Map<String, String>>();
    public static final Map<String,List<DictView>> dictsList=new HashMap<String,List<DictView>>();

    public static Map<String, String> getValuesByKeyName(String keyName) {
        synchronized (dicts) {
            if (dicts.size() == 0) {
                generateVaelusMap();
            }
        }
        Map<String, String> dict = dicts.get(keyName);
        return dict == null ? new HashMap<String, String>() : dict;
    }

    private static void generateVaelusMap() {
        List<DictView> dictList = DictView.findAllDictViews();
        for (Iterator<DictView> it = dictList.iterator(); it.hasNext(); ) {
            DictView dv = it.next();
            Map<String, String> values = dicts.get(dv.getKeyName());
            if (values == null) {
                values = new HashMap<String, String>();
                dicts.put(dv.getKeyName(), values);
            }
            values.put(dv.getKeyValue(), dv.getKeyDesc());
        }
    }
    private static void generateValuesList(){
    	List<DictView> dictList = DictView.findAllDictViews();
    	for (Iterator<DictView> it = dictList.iterator(); it.hasNext(); ) {
    		DictView dv = it.next();
    		List<DictView> valueList=dictsList.get(dv.getKeyName());
    		if(valueList==null){
    			valueList=new ArrayList<DictView>();
    			dictsList.put(dv.getKeyName(), valueList);
    		}
    		valueList.add(dv);
    	}
    }
    public static  String getDescByKeyName(String keyName,String keyValue){
    	Map<String, String> value= getValuesByKeyName(keyName);
    	return value.get(keyValue);
    }
	public static List<DictView> getListByKeyName(String keyName){
		 synchronized (dictsList) {
	            if (dictsList.size() == 0) {
	            	generateValuesList();
	            }
	        }
		return dictsList.get(keyName);
    }
}
