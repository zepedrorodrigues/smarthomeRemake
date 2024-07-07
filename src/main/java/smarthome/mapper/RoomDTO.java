package smarthome.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

/**
 * Data Transfer Object (DTO) for room information.
 * Encapsulates data for a room including the house name, room name, floor, and dimensions.
 */
@Relation(collectionRelation = "rooms", itemRelation = "room")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO extends RepresentationModel<RoomDTO> {


    private String roomId;
    private String houseName;
    private String roomName;
    private Integer floor;
    private Double height;
    private Double width;
    private Double length;

    /**
     * Constructs a new RoomDTO with the given room id.
     * @param roomId the unique identifier of the room
     */
    public RoomDTO(String roomId) {
        this(roomId, null, null, null, null, null, null);
    }

    /**
     * Constructs a new RoomDTO with the given parameters.
     *
     * @param roomId    the unique identifier of the room
     * @param houseName the name of the house the room belongs to, the unique identifier of the house
     * @param roomName  the name of the room
     * @param floor     the floor on which the room is located
     * @param height    the height of the room, needed to create dimensions
     * @param width     the width of the room, needed to create dimensions
     * @param length    the length of the room, needed to create dimensions
     */
    public RoomDTO(String roomId, String houseName, String roomName, Integer floor, Double height, Double width,
                   Double length) {
        this.roomId = roomId;
        this.houseName = houseName;
        this.roomName = roomName;
        this.floor = floor;
        this.height = height;
        this.width = width;
        this.length = length;
    }

    /**
     * Constructs a new RoomDTO without a house name and without room id.
     * The house name is set to null.
     *
     * @param roomName the name of the room
     * @param floor    the floor on which the room is located
     * @param height   the height of the room, needed to create dimensions
     * @param width    the width of the room, needed to create dimensions
     * @param length   the length of the room, needed to create dimensions
     */
    public RoomDTO(String roomName, Integer floor, Double height, Double width, Double length) {
        this.roomName = roomName;
        this.floor = floor;
        this.height = height;
        this.width = width;
        this.length = length;
    }

    /**
     * Constructs a new RoomDTO without room id.
     * The house name is set to null.
     *
     * @param roomName the name of the room
     * @param houseName the name of the house
     * @param floor    the floor on which the room is located
     * @param height   the height of the room, needed to create dimensions
     * @param width    the width of the room, needed to create dimensions
     * @param length   the length of the room, needed to create dimensions
     */
    public RoomDTO(String roomName, String houseName, int floor, double height, double width, double length) {
        this.roomName = roomName;
        this.houseName = houseName;
        this.floor = floor;
        this.height = height;
        this.width = width;
        this.length = length;
    }

    /**
     * Default constructor.
     */
    public RoomDTO() {
    }

    /**
     * Returns the unique identifier of the room.
     *
     * @return the unique identifier of the room
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * Returns the name of the house the room belongs to.
     *
     * @return the name of the house
     */
    public String getHouseName() {
        return houseName;
    }

    /**
     * Returns the name of the room.
     *
     * @return the name of the room
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Returns the floor on which the room is located.
     *
     * @return the floor of the room
     */
    public Integer getFloor() {
        return floor;
    }

    /**
     * Returns the height of the room.
     *
     * @return the height of the room
     */
    public Double getHeight() {
        return height;
    }

    /**
     * Returns the width of the room.
     *
     * @return the width of the room
     */
    public Double getWidth() {
        return width;
    }

    /**
     * Returns the length of the room.
     *
     * @return the length of the room
     */
    public Double getLength() {
        return length;
    }
}
