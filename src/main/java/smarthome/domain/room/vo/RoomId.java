package smarthome.domain.room.vo;

import smarthome.ddd.DomainId;

import java.util.Objects;

/**
 * Value object for the identifier of a room.
 */
public class RoomId implements DomainId {

    private final String roomId;


    /**
     * Constructor for the class RoomId.
     *
     * @param roomId the identifier of the room.
     * @throws IllegalArgumentException if the identifier is null or blank.
     */
    public RoomId(String roomId) {
        if (roomId == null || roomId.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.roomId = roomId;
    }

    /**
     * Getter for the identifier of the room.
     *
     * @return the identifier of the room.
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * Method to compare two RoomId objects.
     *
     * @param o the object to compare with.
     * @return true if the objects are the same, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomId oRoomId = (RoomId) o;
        return Objects.equals(this.roomId, oRoomId.roomId);
    }

    /**
     * Method to get the hash code of the RoomId object.
     *
     * @return the hash code of the RoomId object.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(roomId);
    }
}
