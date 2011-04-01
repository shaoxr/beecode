package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;userName&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;password&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;title&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;userNumbers&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;MMSContent&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}base64Binary&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;sendType&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}int&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "userName", "password", "title",
		"userNumbers", "mmsContent", "sendType" })
@XmlRootElement(name = "SendMMS")
public class SendMMS {

	protected String userName;
	protected String password;
	protected String title;
	protected String userNumbers;
	@XmlElement(name = "MMSContent")
	protected byte[] mmsContent;
	protected int sendType;

	/**
	 * Gets the value of the userName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the value of the userName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserName(String value) {
		this.userName = value;
	}

	/**
	 * Gets the value of the password property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the value of the password property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPassword(String value) {
		this.password = value;
	}

	/**
	 * Gets the value of the title property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the value of the title property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTitle(String value) {
		this.title = value;
	}

	/**
	 * Gets the value of the userNumbers property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserNumbers() {
		return userNumbers;
	}

	/**
	 * Sets the value of the userNumbers property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserNumbers(String value) {
		this.userNumbers = value;
	}

	/**
	 * Gets the value of the mmsContent property.
	 * 
	 * @return possible object is byte[]
	 */
	public byte[] getMMSContent() {
		return mmsContent;
	}

	/**
	 * Sets the value of the mmsContent property.
	 * 
	 * @param value
	 *            allowed object is byte[]
	 */
	public void setMMSContent(byte[] value) {
		this.mmsContent = ((byte[]) value);
	}

	/**
	 * Gets the value of the sendType property.
	 * 
	 */
	public int getSendType() {
		return sendType;
	}

	/**
	 * Sets the value of the sendType property.
	 * 
	 */
	public void setSendType(int value) {
		this.sendType = value;
	}

}
