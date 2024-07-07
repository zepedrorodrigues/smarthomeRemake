package smarthome.domain.house.vo;

import smarthome.ddd.ValueObject;
import smarthome.utils.AvailableCountries;

import java.util.Objects;

/**
 * Represents an Address in the domain.
 * An Address is a value object that consists of a StreetName, StreetNumber, ZipCode, City and Country.
 */

public class Address implements ValueObject {

    private final StreetName streetName;
    private final StreetNumber streetNumber;
    private final ZipCode zipCode;
    private final City city;
    private final Country country;

    /**
     * Constructs an Address with the given StreetName, StreetNumber, ZipCode, City and Country.
     *
     * @param streetName   the street name of the address
     * @param streetNumber the street number of the address
     * @param zipCode      the zip code of the address
     * @param city         the city of the address
     * @param country      the country of the address
     * @throws IllegalArgumentException if any of the parameters are null
     */
    public Address(StreetName streetName, StreetNumber streetNumber, ZipCode zipCode, City city, Country country) {
        if (streetName == null || streetNumber == null || zipCode == null || city == null || country == null || !isValidZipCodeForCountry(country, zipCode)) {
            throw new IllegalArgumentException();
        }

        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    /**
     * Getter for the StreetName of the Address.
     *
     * @return the StreetName of the Address
     */
    public StreetName getStreetName() {
        return streetName;
    }

    /**
     * Returns the StreetNumber of the address.
     *
     * @return the street number of the address
     */
    public StreetNumber getStreetNumber() {
        return streetNumber;
    }

    /**
     * Returns the ZipCode of the address.
     *
     * @return the zip code of the address
     */
    public ZipCode getZipCode() {
        return zipCode;
    }

    /**
     * Returns the City of the address.
     *
     * @return the city of the address
     */
    public City getCity() {
        return city;
    }

    /**
     * Returns the Country of the address, must be one of the available countries.
     *
     * @return the country of the address
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Checks if the provided zip code is valid for the given country.
     *
     * @param country the country to check the zip code format against
     * @param zipCode the zip code to check
     * @return true if the zip code is valid for the given country, false otherwise
     */
    private boolean isValidZipCodeForCountry(Country country, ZipCode zipCode) {
        for (AvailableCountries countryToCheck : AvailableCountries.values()) {
            if (country.getCountryName() != null && country.getCountryName().equalsIgnoreCase(countryToCheck.getCountry()) && zipCode.getZipCode().matches(countryToCheck.getZipCodeFormat())) {
                return true;
            }
        }
        return false; // Country not found
    }

    /**
     * Compares this Address to the specified object.
     * The result is true if and only if the argument is not null and is a Address object that represents the same
     * address as this object.
     *
     * @param o the object to compare this  Address  against
     * @return true if the given object represents a Address  equivalent to this Address, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return streetName.equals(address.streetName) && streetNumber.equals(address.streetNumber) && zipCode.equals(address.zipCode) && city.equals(address.city) && country.equals(address.country);
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as
     * those provided by HashMap.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(streetName, streetNumber, zipCode, city, country);
    }
}
