package smarthome.domain.house.vo;

import smarthome.ddd.DomainId;

/**
 * House name class, is a value object that represents the house identifier
 */
public class HouseName implements DomainId {

    private final String name;

    /**
     * Constructor of the class HouseName
     *
     * @param name String that represents the house name, that is the house identifier
     * @throws IllegalArgumentException if the houseName is null, empty or blank
     */
    public HouseName(String name) {
        if (isIdValid(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Getter of the house name
     *
     * @return String that represents the house identifier
     */
    public String getName() {
        return name;
    }

    /**
     * Method that checks if the houseName is valid
     *
     * @param houseName String that represents the house name, that is the house identifier
     * @return boolean that represents if the houseName is valid
     */
    private boolean isIdValid(String houseName) {
        return houseName != null && !houseName.isBlank();
    }

    /**
     * Method equals that checks if the object is the same as the house name
     *
     * @param o Object that represents the house name to compare
     * @return boolean that represents if the object is the same as the house name
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HouseName that = (HouseName) o;
        return name.equals(that.name);
    }

    /**
     * Method that returns the hashcode of the house name
     *
     * @return int that represents the hashcode of the house name
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
