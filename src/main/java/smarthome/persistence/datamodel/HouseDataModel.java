package smarthome.persistence.datamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.house.House;

/**
 * House Data Model that represents the house entity in the database
 */
@Entity
@Table(name = "HOUSE")
public class HouseDataModel {

    @Id
    private String houseName;

    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;


    /**
     * Empty constructor of the House Data Model
     */
    public HouseDataModel() {
    }

    /**
     * Constructor of the House Data Model that receives a house entity
     *
     * @param house the house entity
     * @throws IllegalArgumentException if the house is null
     */
    public HouseDataModel(House house) {
        if (house == null) {
            throw new IllegalArgumentException();
        }
        this.houseName = house.getIdentity().getName();
        this.streetName = house.getLocation().getAddress().getStreetName().getStreetName();
        this.streetNumber = house.getLocation().getAddress().getStreetNumber().getStreetNumber();
        this.zipCode = house.getLocation().getAddress().getZipCode().getZipCode();
        this.city = house.getLocation().getAddress().getCity().getCity();
        this.country = house.getLocation().getAddress().getCountry().getCountryName();
        this.latitude = house.getLocation().getGps().getLatitude().getLatitude();
        this.longitude = house.getLocation().getGps().getLongitude().getLongitude();
    }

    /**
     * Empty constructor of the House Data Model
     *
     * @return the house name
     */
    public String getHouseName() {
        return houseName;
    }

    /**
     * Get the street name of the house
     *
     * @return the street name
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Get the street number of the house
     *
     * @return the street number
     */
    public String getStreetNumber() {
        return streetNumber;
    }

    /**
     * Get the zip code of the house
     *
     * @return the zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Get the city of the house
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Get the country of the house
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Get the latitude of the house
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Get the longitude of the house
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }


    /**
     * Update the house data model from a house entity
     * If the house entity is null or the house name is different from the data model, return false
     * Otherwise, update the house data model with the house entity data and return true
     *
     * @param house the house entity
     * @return true if the house data model is updated, false otherwise
     */
    public boolean updateFromDomain(House house) {
        if (house == null || !house.getIdentity().toString().equals(houseName)) {
            return false;
        }
        this.streetName = house.getLocation().getAddress().getStreetName().getStreetName();
        this.streetNumber = house.getLocation().getAddress().getStreetNumber().getStreetNumber();
        this.zipCode = house.getLocation().getAddress().getZipCode().getZipCode();
        this.city = house.getLocation().getAddress().getCity().getCity();
        this.country = house.getLocation().getAddress().getCountry().getCountryName();
        this.latitude = house.getLocation().getGps().getLatitude().getLatitude();
        this.longitude = house.getLocation().getGps().getLongitude().getLongitude();
        return true;
    }


}
