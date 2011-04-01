/*
 * Created on 2004-7-11
 *
 * Project: jasine.base
 */
package com.newland.utils;

/**
 * FormatElement是所有格式化器的基类
 * Project: jasine.base
 * @author shen
 */
public abstract class Formatter {
    /**
     * 格式化器名称
     */
    String name;
    
    public Formatter(String name) {
        this.name = name;
    }
    
    public Formatter() {
        
    }
    
    /**
     * 从输入字符串中抽取与该格式化器对应的子字符串数组，该方法被unformat方法调用
     * 主要用于组合数据元素，默认返回完整的输入字符
     * @param aString
     * @return
     */
    public String[] extract(String aString) {
        return new String[]{aString};
    }
    
    /**
     * 将输入对象装换为字符串
     * @param aObject
     * @return
     */
    public abstract String format(Object aObject);
    
    /**
     * 将字符串表示的数据转换为业务对象
     * @param aString
     * @return
     */
    public abstract Object unformat(String aString);
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
