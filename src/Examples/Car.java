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
 * <p>Java class for Car complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Car">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="carID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="make" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="model" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="transmission" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="noOfDoors" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fuelType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="noOfSeats" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="islend" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Car", propOrder = {
    "carID",
    "make",
    "model",
    "transmission",
    "noOfDoors",
    "fuelType",
    "noOfSeats",
    "islend",
    "type"
})
public class Car {

    @XmlElement(required = true)
    protected String carID;
    @XmlElement(required = true)
    protected String make;
    @XmlElement(required = true)
    protected String model;
    @XmlElement(required = true)
    protected String transmission;
    protected int noOfDoors;
    @XmlElement(required = true)
    protected String fuelType;
    protected int noOfSeats;
    @XmlElement(required = true)
    protected String islend;
    @XmlElement(required = true)
    protected String type;

    /**
     * Gets the value of the carID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getislend() {
        return islend;
    }

    /**
     * Sets the value of the carID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setislend(String value) {
        this.islend = value;
    }

    /**
     * Gets the value of the make property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    
    /**
     * Gets the value of the carID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarID() {
        return carID;
    }

    /**
     * Sets the value of the carID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarID(String value) {
        this.carID = value;
    }

    /**
     * Gets the value of the make property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMake() {
        return make;
    }

    /**
     * Sets the value of the make property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMake(String value) {
        this.make = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the transmission property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransmission() {
        return transmission;
    }

    /**
     * Sets the value of the transmission property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransmission(String value) {
        this.transmission = value;
    }

    /**
     * Gets the value of the noOfDoors property.
     * 
     */
    public int getNoOfDoors() {
        return noOfDoors;
    }

    /**
     * Sets the value of the noOfDoors property.
     * 
     */
    public void setNoOfDoors(int value) {
        this.noOfDoors = value;
    }

    /**
     * Gets the value of the fuelType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFuelType() {
        return fuelType;
    }

    /**
     * Sets the value of the fuelType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFuelType(String value) {
        this.fuelType = value;
    }

    /**
     * Gets the value of the noOfSeats property.
     * 
     */
    public int getNoOfSeats() {
        return noOfSeats;
    }

    /**
     * Sets the value of the noOfSeats property.
     * 
     */
    public void setNoOfSeats(int value) {
        this.noOfSeats = value;
    }
    
    /**
     * Gets the value of the fuelType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarType() {
        return type;
    }

    /**
     * Sets the value of the fuelType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarType(String value) {
        this.type = value;
    }

	



}
