/*
 * Created on 2004-7-24
 *
 * Project: CashManagement
 */
package com.newland.utils;

public class CellDefine {
    
    String name;
    String path;
    String defaultValue;
    
    String styleName;
    boolean count=false;
    
    String cellFormat;
    Formatter formatter;
    
    Short width;
    
    String align;
    
    
    
    /**
     * @return Returns the defaultValue.
     */
    public String getDefaultValue() {
        return defaultValue;
    }
    /**
     * @return Returns the formatter.
     */
    public Formatter getFormatter() {
        return formatter;
    }
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @return Returns the path.
     */
    public String getPath() {
        return path;
    }
    /**
     * @return Returns the styleName.
     */
    public String getStyleName() {
        return styleName;
    }
    /**
     * @param defaultValue The defaultValue to set.
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    /**
     * @param formatter The formatter to set.
     */
    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @param path The path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }
    /**
     * @param styleName The styleName to set.
     */
    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }
    
    
    /**
     * @return Returns the width.
     */
    public Short getWidth() {
        return width;
    }
    /**
     * @param width The width to set.
     */
    public void setWidth(Short width) {
        this.width = width;
    }
    
    
    /**
     * @return Returns the cellFormat.
     */
    public String getCellFormat() {
        return cellFormat;
    }
    /**
     * @param cellFormat The cellFormat to set.
     */
    public void setCellFormat(String cellFormat) {
        this.cellFormat = cellFormat;
    }
    
    
    /**
     * @return Returns the align.
     */
    public String getAlign() {
        return align;
    }
    /**
     * @param align The align to set.
     */
    public void setAlign(String align) {
        this.align = align;
    }
	public boolean isCount() {
		return count;
	}
	public void setCount(boolean count) {
		this.count = count;
	}
}