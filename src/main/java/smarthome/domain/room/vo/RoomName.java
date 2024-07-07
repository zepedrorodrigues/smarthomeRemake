package smarthome.domain.room.vo;

import smarthome.ddd.ValueObject;

/**
 * Room name class, is a value object that represents the room identifier
 */

public class RoomName implements ValueObject {
    private final String _roomName;

    /**
     * Constructor of the class RoomName
     *
     * @param roomName String that represents the room name, that is the room identifier
     * @throws IllegalArgumentException if the roomName is null or blank
     */
    public RoomName(String roomName) {
        if (roomName == null || roomName.isBlank()) {
            throw new IllegalArgumentException();
        }
        this._roomName = roomName;
    }

    /**
     * Getter of the room name
     *
     * @return String that represents the room identifier
     */
    public String getRoomName() {
        return this._roomName;
    }
    /**
     * Method that checks if the object is equal to the room name
     *
     * @param o the object to compare
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoomName roomName1 = (RoomName) o;
        return this._roomName.equals(roomName1._roomName);
    }

    /**
     * Method that generates a hash code for the RoomName object
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return this._roomName.hashCode();
    }
}
