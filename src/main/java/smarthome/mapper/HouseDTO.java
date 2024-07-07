package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

/**
 * Data Transfer Object (DTO) for house information.
 * Encapsulates data for a house including name, address, geographical coordinates, and other relevant attributes.
 */
public class HouseDTO extends RepresentationModel<HouseDTO> {
    private String houseName;
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;
    private String country;
    private double latitude;
    private double longitude;
    /**
     * Constructs a new HouseDTO with the specified details.
     *
     * @param houseName    the name of the house
     * @param streetName   the name of the street
     * @param streetNumber the street number of the house
     * @param zipCode      the postal code of the house location
     * @param city         the city where the house is located
     * @param country      the country where the house is located
     * @param latitude     the geographical latitude of the house
     * @param longitude    the geographical longitude of the house
     */
    public HouseDTO(String houseName, String streetName, String streetNumber, String zipCode, String city,
                    String country, double latitude, double longitude) {
        this.houseName = houseName;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;

        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Constructs a new HouseDTO with the specified details.
     *
     * @param streetName   the name of the street
     * @param streetNumber the street number of the house
     * @param zipCode      the postal code of the house location
     * @param city         the city where the house is located
     * @param country      the country where the house is located
     * @param latitude     the geographical latitude of the house
     * @param longitude    the geographical longitude of the house
     */
    public HouseDTO(String streetName, String streetNumber, String zipCode, String city, String country,
                    double latitude, double longitude) {
        this.houseName = null;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * An empty constructor for JSON mapping.
     */
    public HouseDTO() {
    }

    /**
     * Returns the name of the house.
     * @return the house name
     */
    public String getHouseName() {
        return houseName;
    }

    /** Returns the street name of the house location.
     *
     * @return the street name
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Returns the street number of the house.
     *
     * @return the street number
     */
    public String getStreetNumber() {
        return streetNumber;
    }

    /**
     * Returns the zip code of the house location.
     *
     * @return the zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Returns the city of the house location.
     *
     * @return the city name
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the country of the house location.
     *
     * @return the country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns the latitude of the house location.
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns the longitude of the house location.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }


}
