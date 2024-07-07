package smarthome.domain.house.vo;

import smarthome.ddd.ValueObject;
import smarthome.utils.AvailableCountries;

/**
 * Represents a Zip Code value object.
 *
 * <p>
 * This class encapsulates the behavior of a Zip Code within a domain-driven design context.
 * A Zip Code is a value object that represents a postal code used for addressing purposes.
 * Instances of this class are immutable once created.
 * </p>
 */
public class ZipCode implements ValueObject {

    private final String zipCode;

    /**
     * Constructs a ZipCode object with the specified zip code.
     *
     * <p>
     * This constructor initializes a ZipCode object with the provided zip code.
     * It performs input validation to ensure that the zip code is not null or empty.
     * If the provided zip code is invalid, an IllegalArgumentException is thrown.
     * </p>
     *
     * @param zipCode the zip code to be encapsulated by this object
     * @throws IllegalArgumentException if the provided zip code is null or blank
     */
    public ZipCode(String zipCode) {
        if (!isValidZipCode(zipCode)) {
            throw new IllegalArgumentException();
        }
        this.zipCode = zipCode;
    }

    /**
     * Retrieves the zip code encapsulated by this object.
     *
     * <p>
     * This method returns the zip code value stored within this ZipCode object.
     * It allows access to the zip code information while preserving the immutability of the object.
     * </p>
     *
     * @return the zip code value encapsulated by this object
     */
    public String getZipCode() {
        return zipCode;
    }


    /**
     * Checks if the provided zip code is valid.
     *
     * @param zipCode the zip code to be validated
     * @return true if the zip code is in an available zip code format in the system, false otherwise
     */
    private boolean isValidZipCode(String zipCode) {
        if (zipCode != null && !zipCode.isBlank()) {
            AvailableCountries[] countries = AvailableCountries.values();
            for (AvailableCountries availableCountry : countries) {
                if (zipCode.trim().matches(availableCountry.getZipCodeFormat())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Generates a hash code value for the ZipCode object.
     *
     * @return a hash code value for this object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZipCode zipCode1 = (ZipCode) o;
        return zipCode.equals(zipCode1.zipCode);
    }

    /**
     * Generates a hash code value for the ZipCode object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return zipCode.hashCode();
    }
}
