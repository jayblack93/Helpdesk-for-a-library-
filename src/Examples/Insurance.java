//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.26 at 11:58:26 AM GMT 
//


package Examples;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Insurance complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Insurance">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="insuranceID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="priceOfExcess" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="policyDetails" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Insurance", propOrder = {
    "insuranceID",
    "priceOfExcess",
    "policyDetails"
})
public class Insurance {

    @XmlElement(required = true)
    protected String insuranceID;
    protected int priceOfExcess;
    @XmlElement(required = true)
    protected String policyDetails;

    /**
     * Gets the value of the insuranceID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuranceID() {
        return insuranceID;
    }

    /**
     * Sets the value of the insuranceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuranceID(String value) {
        this.insuranceID = value;
    }

    /**
     * Gets the value of the priceOfExcess property.
     * 
     */
    public int getPriceOfExcess() {
        return priceOfExcess;
    }

    /**
     * Sets the value of the priceOfExcess property.
     * 
     */
    public void setPriceOfExcess(int value) {
        this.priceOfExcess = value;
    }

    /**
     * Gets the value of the policyDetails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyDetails() {
        return policyDetails;
    }

    /**
     * Sets the value of the policyDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyDetails(String value) {
        this.policyDetails = value;
    }

}