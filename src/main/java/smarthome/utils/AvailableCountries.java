package smarthome.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * The AvailableCountries enum represents the available countries in the system.
 * Each country has a specific zip code format.
 */
public enum AvailableCountries {

    USA("United States of America", "\\d{5}"),
    FRANCE("France", "\\d{5}"),
    PORTUGAL("Portugal", "\\d{4}-\\d{3}"),
    SPAIN("Spain", "\\d{5}");

    private final String country;
    private final String zipCodeFormat;

    /**
     * Constructs an AvailableCountries enum with the specified country and zip code format.
     *
     * @param country       the country represented by this enum
     * @param zipCodeFormat the zip code format for the country represented by this enum
     */
    AvailableCountries(String country, String zipCodeFormat) {
        this.country = country;
        this.zipCodeFormat = zipCodeFormat;
    }

    /**
     * Retrieves the country represented by this enum.
     *
     * @return the country represented by this enum
     */
    public String getCountry() {
        return country;
    }

    /**
     * Retrieves the zip code format for the country represented by this enum.
     *
     * @return the zip code format for the country represented by this enum
     */
    public String getZipCodeFormat() {
        return zipCodeFormat;
    }

    /**
     * Retrieves a list of all country names represented by this enum.
     *
     * @return A list containing the names of all countries available in the system.
     */
    public static List<String> getAvailableCountries() {
        List<String> availableCountries = new ArrayList<>();
        for (AvailableCountries value : values()) {
            availableCountries.add(value.getCountry());
        }
        return availableCountries;
    }
}
