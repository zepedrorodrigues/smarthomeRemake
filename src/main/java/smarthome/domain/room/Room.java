package smarthome.domain.room;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.room.vo.Dimensions;
import smarthome.domain.room.vo.Floor;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.room.vo.RoomName;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a room within a smart home environment.
 * A room is considered an aggregate root in the domain-driven design context,
 * uniquely identified by a {@link RoomId}. It also includes additional
 * attributes such as {@link RoomName} its designation, the {@link HouseName} it belongs to, its location within the
 * house as indicated by {@link Floor}, and its physical {@link Dimensions}.
 */
public class Room implements AggregateRoot<RoomId> {

    private RoomId roomId;
    private RoomName roomName;
    private HouseName houseName;
    private Floor floor;
    private Dimensions dimensions;

    /**
     * Constructs a new Room instance with specified characteristics.
     *
     * @param roomName   the unique identifier of the room, must not be null
     * @param houseName  the name of the house the room belongs to, must not be null
     * @param floor      the floor on which the room is located, must not be null
     * @param dimensions the physical dimensions of the room, must not be null
     * @throws IllegalArgumentException if any of the parameters are null, indicating invalid input
     */
    protected Room(RoomName roomName, HouseName houseName, Floor floor, Dimensions dimensions) {
        if (!validParameters(roomName, houseName, floor, dimensions)) {
            throw new IllegalArgumentException();
        }
        this.roomId = new RoomId(UUID.randomUUID().toString());
        this.roomName = roomName;
        this.houseName = houseName;
        this.floor = floor;
        this.dimensions = dimensions;
    }

    /**
     * Constructs a new Room instance with specified characteristics. (including roomId,
     * necessary for system persistence)
     *
     * @param roomId     the unique identifier of the room, must not be null
     * @param roomName   the unique identifier of the room, must not be null
     * @param houseName  the name of the house the room belongs to, must not be null
     * @param floor      the floor on which the room is located, must not be null
     * @param dimensions the physical dimensions of the room, must not be null
     * @throws IllegalArgumentException if any of the parameters are null, indicating invalid input
     */
    protected Room(RoomId roomId, RoomName roomName, HouseName houseName, Floor floor, Dimensions dimensions) {
        if (!validParameters(roomId, roomName, houseName, floor, dimensions)) {
            throw new IllegalArgumentException();}
        this.roomId = roomId;
        this.roomName = roomName;
        this.houseName = houseName;
        this.floor = floor;
        this.dimensions = dimensions;
    }

    /**
     * Validates the parameters required for constructing a Room instance.
     *
     * @param roomName   the room's name
     * @param houseName  the name of the house
     * @param floor      the floor information
     * @param dimensions the room's dimensions
     * @return true if all parameters are non-null; false otherwise
     */
    private boolean validParameters(RoomName roomName, HouseName houseName, Floor floor, Dimensions dimensions) {
        return roomName != null && houseName != null && floor != null && dimensions != null;
    }

    /**
     * Validates the parameters required for constructing a Room instance.
     * @param roomId the room's id
     * @param roomName the room's name
     * @param houseName the name of the house
     * @param floor the floor information
     * @param dimensions the room's dimensions
     * @return true if all parameters are non-null; false otherwise
     */
    private boolean validParameters(RoomId roomId, RoomName roomName, HouseName houseName, Floor floor,
                                    Dimensions dimensions) {
        return roomId != null && roomName != null && houseName != null && floor != null && dimensions != null;
    }

    /**
     * Retrieves the unique identifier of this Room.
     *
     * @return the room's unique identifier as a {@link RoomName}
     */
    @Override
    public RoomId getIdentity() {
        return roomId;
    }


    /**
     * Gets the name of this room.
     *
     * @return the {@link RoomName} of the room
     */
    public RoomName getRoomName() {
        return roomName;

    }

    /**
     * Gets the name of the house this room belongs to.
     *
     * @return the {@link HouseName} of the house
     */
    public HouseName getHouseName() {
        return houseName;
    }

    /**
     * Gets the floor information of this room.
     *
     * @return the {@link Floor} indicating the room's location within the house
     */
    public Floor getFloor() {
        return floor;
    }

    /**
     * Gets the physical dimensions of this room.
     *
     * @return the {@link Dimensions} of the room
     */
    public Dimensions getDimensions() {
        return dimensions;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Room room = (Room) object;
        return roomId.equals(room.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId);
    }
}
