package smarthome.domain.house.vo;

import smarthome.ddd.ValueObject;

/**
 * This Class is a Value Object representing a City within the context of a Domain-Driven Design (DDD) project.
 * <p>
 * The city represents a specific geographic location (a city) where a house is located.
 * and is used to store the city of a house in the domain of a smart home system.
 * <p>
 * The city is a value object, immutable, and a string that cannot be null or blank.
 * The city value object is part of the house aggregate, used in the House entity.
 * <p>
 * By treating City as a value object, we ensure that the identity of a city
 * is solely determined by its name. This simplifies domain logic by allowing
 * comparisons and operations based solely on the city name, without the need
 * to consider additional attributes or behavior.
 * </p>
 */
public class City implements ValueObject {

    private final String city;

    /**
     * Constructs a City with the provided city name.
     *
     * @param homeCity the name of the city. It must not be null or empty.
     * @throws IllegalArgumentException if the provided city name is null or blank
     */
    public City(String homeCity) {
        if (homeCity == null || homeCity.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.city = homeCity;
    }

    /**
     * Retrieves the name of the city.
     *
     * @return The name of the city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Compares this City with the specified object for equality.
     * Returns true if the given object is also a City and the two City objects
     * represent the same city (have the same city name), false otherwise.
     *
     * @param o the object to compare this City with
     * @return true if the given object is a City with the same city name, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city1 = (City) o;
        return city.equals(city1.city);
    }

    /**
     * Generates a hash code value for the City object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return city.hashCode();
    }
}
