package smarthome.domain.house.vo;

import smarthome.ddd.ValueObject;
import smarthome.utils.AvailableCountries;

/**
 * Represents a Country value object.
 */
public class Country implements ValueObject {

    private final String countryName;

    /**
     * Constructs a Country object with the specified country.
     *
     * @param countryName the country to be encapsulated by this object
     * @throws IllegalArgumentException if the provided country is not valid
     */
    public Country(String countryName) {
        if (!isValidCountry(countryName)) {
            throw new IllegalArgumentException();
        }
        this.countryName = countryName;
    }

    /**
     * Retrieves the country name encapsulated by this object.
     *
     * @return the country value encapsulated by this object
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Checks if the provided country is valid.
     *
     * @param country the country to be validated
     * @return true if the country is an available country in the system, false otherwise
     */
    private boolean isValidCountry(String country) {
        if (country != null && !country.isBlank()) {
            AvailableCountries[] countries = AvailableCountries.values();
            for (AvailableCountries availableCountry : countries) {
                if (country.trim().equalsIgnoreCase(availableCountry.getCountry())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Compares this Country object with the specified object for equality.
     *
     * @param o the object to compare this Country with
     * @return true if the given object is a Country with the
     * same country name, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Country country1 = (Country) o;
        return countryName.equals(country1.countryName);
    }

    /**
     * Generates a hash code for this Country object.
     *
     * @return the hash code generated for this object
     */
    @Override
    public int hashCode() {
        return countryName.hashCode();
    }
}
