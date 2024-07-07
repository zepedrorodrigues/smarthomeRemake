package smarthome.domain.room.vo;

import smarthome.ddd.ValueObject;

import java.util.Objects;

/**
 * The Dimensions class represents the dimensions of an object in a 3D space.
 * It implements the ValueObject interface, indicating that its equality is based on its value rather than identity.
 * It contains three fields: width, height, and length, each represented by their respective classes.
 * The class provides getter methods for these fields and an overridden sameAs method for equality check.
 */
public class Dimensions implements ValueObject {
    private final Width width;
    private final Height height;
    private final Length lenght;

    /**
     * Constructs a Dimensions object with the given width, height, and length.
     * If any of the parameters are null, it throws an IllegalArgumentException.
     *
     * @param width  the width of the object
     * @param height the height of the object
     * @param lenght the length of the object
     */
    public Dimensions(Width width, Height height, Length lenght) {
        if (width == null || height == null || lenght == null) {
            throw new IllegalArgumentException();
        }
        this.width = width;
        this.height = height;
        this.lenght = lenght;
    }

    /**
     * Returns the width of the object.
     *
     * @return the width of the object
     */
    public Width getWidth() {
        return width;
    }

    /**
     * Returns the height of the object.
     *
     * @return the height of the object
     */
    public Height getHeight() {
        return height;
    }

    /**
     * Returns the length of the object.
     *
     * @return the length of the object
     */
    public Length getLength() {
        return lenght;}

    /**
     * Returns the hash code value for this Dimensions object.
     *
     * @return the hash code value for this Dimensions object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dimensions that = (Dimensions) o;
        return width.equals(that.width) &&
                height.equals(that.height) &&
                lenght.equals(that.lenght);}

    /**
     * Returns the hash code value for this Dimensions object.
     *
     * @return the hash code value for this Dimensions object
     */
    @Override
    public int hashCode() {
        return Objects.hash(width, height, lenght);
    }
}
